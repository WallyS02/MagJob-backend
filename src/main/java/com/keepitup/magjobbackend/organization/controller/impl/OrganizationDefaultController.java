package com.keepitup.magjobbackend.organization.controller.impl;

import com.keepitup.magjobbackend.configuration.Constants;
import com.keepitup.magjobbackend.configuration.KeycloakController;
import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.jwt.CustomJwt;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final SecurityService securityService;

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
            KeycloakController keycloakController,
            SecurityService securityService
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
        this.securityService = securityService;
    }

    @Override
    public GetOrganizationsResponse getOrganizations() {
        return organizationsToResponse.apply(service.findAll());
    }

    @Override
    public GetOrganizationResponse getOrganization(BigInteger id) {
        Organization organization = service.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.find(id)
                .map(organizationToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetOrganizationsResponse getOrganizationsByUser(UUID userId) {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        UUID loggedInUserId = UUID.fromString(jwt.getExternalId());

        if (!loggedInUserId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

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
                            .canManageTasks(true)
                            .canManageRoles(true)
                            .canManageInvitations(true)
                            .canManageAnnouncements(true)
                            .isAdmin(true) // TODO delete in future; For testing purposes Owner will be also Admin
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
        Optional<Organization> organization = service.find(id);

        if (organization.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!securityService.isOwner(organization.get())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        service.delete(id);
    }

    @Override
    public GetOrganizationResponse updateOrganization(BigInteger id, PatchOrganizationRequest patchOrganizationRequest) {
        Optional<Organization> organization = service.find(id);

        if (organization.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!securityService.isOwner(organization.get())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        service.update(updateOrganizationWithRequest.apply(organization.get(), patchOrganizationRequest));
        return getOrganization(id);
    }
}
