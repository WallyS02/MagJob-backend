package com.keepitup.magjobbackend.member.controller.impl;

import com.keepitup.magjobbackend.configuration.KeycloakController;
import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.member.controller.api.MemberController;
import com.keepitup.magjobbackend.member.dto.*;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.function.*;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.api.OrganizationService;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.service.api.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@Log
public class MemberDefaultController implements MemberController {
    private final MemberService service;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final MembersToResponseFunction membersToResponse;
    private final MemberToResponseFunction memberToResponse;
    private final RequestToMemberFunction requestToMember;
    private final UpdateMemberWithRequestFunction updateMemberWithRequest;
    private final KeycloakController keycloakController;
    private final SecurityService securityService;

    @Autowired
    public MemberDefaultController(
            MemberService service,
            UserService userService,
            OrganizationService organizationService,
            MembersToResponseFunction membersToResponse,
            MemberToResponseFunction memberToResponse,
            RequestToMemberFunction requestToMember,
            UpdateMemberWithRequestFunction updateMemberWithRequest,
            KeycloakController keycloakController,
            SecurityService securityService
    ) {
        this.service = service;
        this.userService = userService;
        this.organizationService = organizationService;
        this.membersToResponse = membersToResponse;
        this.memberToResponse = memberToResponse;
        this.requestToMember = requestToMember;
        this.updateMemberWithRequest = updateMemberWithRequest;
        this.keycloakController = keycloakController;
        this.securityService = securityService;
    }

    @Override
    public GetMembersResponse getMembers() {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return membersToResponse.apply(service.findAllByIsStillMember(true));
    }

    @Override
    public GetMembersResponse getMembersByOrganization(BigInteger organizationId) {
        Optional<Organization> organizationOptional = organizationService.find(organizationId);

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return membersToResponse.apply(service.findAllByOrganizationAndIsStillMember(organization, true));
    }

    @Override
    public GetMemberResponse getMember(BigInteger id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.findByIdAndIsStillMember(id, true)
                .map(memberToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMemberResponse createMember(PostMemberRequest postMemberRequest) {
        Optional<List<Organization>> organizations = service.findAllOrganizationsByUserId(postMemberRequest.getUserId());
        Optional<Organization>  organization = organizationService.find(postMemberRequest.getOrganization());
        Optional<User> user = userService.find(postMemberRequest.getUserId());


        if (user.isEmpty() || organization.isEmpty() || organizations.isEmpty()
                || organizations.get().contains(organization.get())
        ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {

            if (!securityService.isOwner(organization.get())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

            service.create(requestToMember.apply(postMemberRequest));

            keycloakController.addUserToKeycloakGroup(organization.get().getName(), user.get().getId());
        }
        return service.findByUserAndOrganization(user.get(), organization.get())
                .map(memberToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteMember(BigInteger id) {
        Member memberToDelete = service.findByIdAndIsStillMember(id, true).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isOwner(memberToDelete.getOrganization())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        keycloakController.removeUserFromKeycloakGroup(
                memberToDelete.getOrganization().getName(),
                memberToDelete.getUser().getId()
        );

        service.delete(id);
    }

    @Override
    public GetMemberResponse updateMember(BigInteger id, PatchMemberRequest patchMemberRequest) {
        Member member = service.findByIdAndIsStillMember(id, true).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isOwner(member.getOrganization())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        service.update(updateMemberWithRequest.apply(member, patchMemberRequest));

        return getMember(id);
    }
}

