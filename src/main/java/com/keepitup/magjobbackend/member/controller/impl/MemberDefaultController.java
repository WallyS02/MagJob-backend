package com.keepitup.magjobbackend.member.controller.impl;

import com.keepitup.magjobbackend.member.controller.api.MemberController;
import com.keepitup.magjobbackend.member.dto.*;
import com.keepitup.magjobbackend.member.function.*;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.api.OrganizationService;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.service.api.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
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

    @Autowired
    public MemberDefaultController(
            MemberService service,
            UserService userService,
            OrganizationService organizationService,
            MembersToResponseFunction membersToResponse,
            MemberToResponseFunction memberToResponse,
            RequestToMemberFunction requestToMember,
            UpdateMemberWithRequestFunction updateMemberWithRequest
    ) {
        this.service = service;
        this.userService = userService;
        this.organizationService = organizationService;
        this.membersToResponse = membersToResponse;
        this.memberToResponse = memberToResponse;
        this.requestToMember = requestToMember;
        this.updateMemberWithRequest = updateMemberWithRequest;
    }

    @Override
    public GetMembersResponse getMembers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return membersToResponse.apply(service.findAllByIsStillMember(true, pageRequest));
    }

    @Override
    public GetMembersResponse getMembersByOrganization(int page, int size, BigInteger organizationId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Optional<Organization> organizationOptional = organizationService.find(organizationId);

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return membersToResponse.apply(service.findAllByOrganizationAndIsStillMember(organization, true, pageRequest));
    }

    @Override
    public GetMemberResponse getMember(BigInteger id) {
        return service.findByIdAndIsStillMember(id, true)
                .map(memberToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMemberResponse createMember(PostMemberRequest postMemberRequest) {
        Optional<Page<Organization>> organizations = service.findAllOrganizationsByUser(postMemberRequest.getUser(), Pageable.unpaged());
        Optional<Organization>  organization = organizationService.find(postMemberRequest.getOrganization());
        Optional<User> user = userService.find(postMemberRequest.getUser());

        if (user.isEmpty() || organization.isEmpty() || organizations.isEmpty()
                || organizations.get().stream().toList().contains(organization.get())
        ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            service.create(requestToMember.apply(postMemberRequest));
        }
        return service.findByUserAndOrganization(user.get(), organization.get())
                .map(memberToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteMember(BigInteger id) {
        service.findByIdAndIsStillMember(id, true)
                .ifPresentOrElse(
                        member -> service.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public GetMemberResponse updateMember(BigInteger id, PatchMemberRequest patchMemberRequest) {
        service.findByIdAndIsStillMember(id, true)
                .ifPresentOrElse(
                        member -> service.update(updateMemberWithRequest.apply(member, patchMemberRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
        return getMember(id);
    }
}

