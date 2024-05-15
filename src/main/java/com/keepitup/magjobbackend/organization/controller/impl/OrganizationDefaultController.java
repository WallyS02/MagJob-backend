package com.keepitup.magjobbackend.organization.controller.impl;

import com.keepitup.magjobbackend.configuration.Constants;
import com.keepitup.magjobbackend.configuration.KeycloakController;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.organization.controller.api.OrganizationController;
import com.keepitup.magjobbackend.organization.dto.*;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.function.*;
import com.keepitup.magjobbackend.organization.service.api.OrganizationService;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.role.service.api.RoleService;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import com.keepitup.magjobbackend.rolemember.service.api.RoleMemberService;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.service.api.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log
public class OrganizationDefaultController implements OrganizationController {
    private final OrganizationService service;
    private final OrganizationsToResponseFunction organizationsToResponse;
    private final OrganizationToResponseFunction organizationToResponse;
    private final RequestToOrganizationFunction requestToOrganization;
    private final UpdateOrganizationWithRequestFunction updateOrganizationWithRequest;
    private final MemberService memberService;
    private final UserService userService;
    private final RoleService roleService;
    private final RoleMemberService roleMemberService;
    private final KeycloakController keycloakController;

    @Autowired
    public OrganizationDefaultController(
            OrganizationService service,
            MemberService memberService,
            UserService userService,
            RoleService roleService,
            RoleMemberService roleMemberService,
            OrganizationsToResponseFunction organizationsToResponse,
            OrganizationToResponseFunction organizationToResponse,
            RequestToOrganizationFunction requestToOrganization,
            UpdateOrganizationWithRequestFunction updateOrganizationWithRequest,
            KeycloakController keycloakController
    ) {
        this.service = service;
        this.memberService = memberService;
        this.userService = userService;
        this.roleService = roleService;
        this.roleMemberService = roleMemberService;
        this.organizationsToResponse = organizationsToResponse;
        this.organizationToResponse = organizationToResponse;
        this.requestToOrganization = requestToOrganization;
        this.updateOrganizationWithRequest = updateOrganizationWithRequest;
        this.keycloakController = keycloakController;
    }

    @Override
    public GetOrganizationsResponse getOrganizations() {
        return organizationsToResponse.apply(service.findAll());
    }

    @Override
    public GetOrganizationResponse getOrganization(BigInteger id) {
        return service.find(id)
                .map(organizationToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetOrganizationsResponse getOrganizationsByUser(UUID userId) {
        Optional<List<Organization>> organizationsOptional = memberService.findAllOrganizationsByUserId(userId);

        List<Organization> organizations = organizationsOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return organizationsToResponse.apply(organizations);
    }

    @Override
    public GetOrganizationResponse createOrganization(PostOrganizationRequest postOrganizationRequest) {
        Map<String, String> roleName2groupExternalId;
        Optional<Organization> organization = service.findByName(postOrganizationRequest.getName());
        if (organization.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            service.create(requestToOrganization.apply(postOrganizationRequest));
            Optional<User> user = userService.find(postOrganizationRequest.getUserId());
            Optional<Organization> createdOrganization = service.findByName(postOrganizationRequest.getName());

            if (user.isPresent() && createdOrganization.isPresent()) {
                roleName2groupExternalId =
                        keycloakController.createGroupRepresentation(createdOrganization.get().getName(), user.get().getId());

                for (String roleName : Constants.DEFAULT_ROLE_NAMES) {
                    roleService.create(Role.builder()
                            .name(roleName)
                            .organization(createdOrganization.get())
                            .externalId(roleName2groupExternalId.get(roleName))
                            .build());
                }

                memberService.create(Member.builder()
                        .pseudonym(Constants.ROLE_NAME_OWNER)
                        .organization(createdOrganization.get())
                        .user(user.get())
                        .build());

                Optional<Member> ownerMember = memberService.findByUserAndOrganization(user.get(), createdOrganization.get());
                Optional<Role> ownerRole = roleService.findByExternalId(roleName2groupExternalId.get(Constants.ROLE_NAME_OWNER));

                if (ownerMember.isPresent() && ownerRole.isPresent()) {
                    roleMemberService.create(RoleMember.builder()
                            .role(ownerRole.get())
                            .member(ownerMember.get())
                            .build());
                }
            }
        }
        return service.findByName(postOrganizationRequest.getName())
                .map(organizationToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteOrganization(BigInteger id) {
        service.find(id)
                .ifPresentOrElse(
                        organization -> service.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public GetOrganizationResponse updateOrganization(BigInteger id, PatchOrganizationRequest patchOrganizationRequest) {
        service.find(id)
                .ifPresentOrElse(
                        organization -> service.update(updateOrganizationWithRequest.apply(organization, patchOrganizationRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
        return getOrganization(id);
    }
}
