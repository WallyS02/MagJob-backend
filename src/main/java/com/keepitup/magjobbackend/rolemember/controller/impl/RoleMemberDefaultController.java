package com.keepitup.magjobbackend.rolemember.controller.impl;

import com.keepitup.magjobbackend.configuration.Constants;
import com.keepitup.magjobbackend.configuration.KeycloakController;
import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.impl.MemberDefaultService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.role.service.impl.RoleDefaultService;
import com.keepitup.magjobbackend.rolemember.controller.api.RoleMemberController;
import com.keepitup.magjobbackend.rolemember.dto.GetRoleMemberResponse;
import com.keepitup.magjobbackend.rolemember.dto.GetRoleMembersResponse;
import com.keepitup.magjobbackend.rolemember.dto.PostRoleMemberRequest;
import com.keepitup.magjobbackend.rolemember.dto.PostRoleMembersRequest;
import com.keepitup.magjobbackend.rolemember.function.RequestToRoleMemberFunction;
import com.keepitup.magjobbackend.rolemember.function.RequestToRoleMembersFunction;
import com.keepitup.magjobbackend.rolemember.function.RoleMemberToResponseFunction;
import com.keepitup.magjobbackend.rolemember.function.RoleMembersToResponseFunction;
import com.keepitup.magjobbackend.rolemember.service.impl.RoleMemberDefaultService;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@Controller
public class RoleMemberDefaultController implements RoleMemberController {
    private final RoleMemberDefaultService roleMemberService;
    private final RoleDefaultService roleService;
    private final MemberDefaultService memberService;
    private final RoleMemberToResponseFunction roleMemberToResponseFunction;
    private final RoleMembersToResponseFunction roleMembersToResponseFunction;
    private final RequestToRoleMemberFunction requestToRoleMemberFunction;
    private final KeycloakController keycloakController;
    private final RequestToRoleMembersFunction requestToRoleMembersFunction;
    private final SecurityService securityService;

    @Autowired
    public RoleMemberDefaultController(
            RoleMemberDefaultService roleMemberService,
            RoleDefaultService roleService,
            MemberDefaultService memberService,
            RoleMemberToResponseFunction roleMemberToResponseFunction,
            RoleMembersToResponseFunction roleMembersToResponseFunction,
            RequestToRoleMemberFunction requestToRoleMemberFunction,
            KeycloakController keycloakController,
            RequestToRoleMembersFunction requestToRoleMembersFunction,
            SecurityService securityService
    ) {
        this.roleMemberService = roleMemberService;
        this.roleService = roleService;
        this.memberService = memberService;
        this.roleMemberToResponseFunction = roleMemberToResponseFunction;
        this.roleMembersToResponseFunction = roleMembersToResponseFunction;
        this.requestToRoleMemberFunction = requestToRoleMemberFunction;
        this.keycloakController = keycloakController;
        this.requestToRoleMembersFunction = requestToRoleMembersFunction;
        this.securityService = securityService;
    }

    @Override
    public GetRoleMembersResponse getRoleMembers() {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return roleMembersToResponseFunction.apply(roleMemberService.findAll());
    }

    @Override
    public GetRoleMemberResponse getRoleMember(BigInteger id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return roleMemberService.find(id)
                .map(roleMemberToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetRoleMembersResponse getRoleMembersByRole(BigInteger roleId) {
        Optional<Role> roleOptional = roleService.find(roleId);

        Role role = roleOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Organization organization = role.getOrganization();

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return roleMembersToResponseFunction.apply(roleMemberService.findAllByRole(role));
    }

    @Override
    public GetRoleMembersResponse getRoleMembersByMember(BigInteger memberId) {
        Optional<Member> memberOptional = memberService.find(memberId);

        Member member = memberOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Organization organization = member.getOrganization();

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return roleMembersToResponseFunction.apply(roleMemberService.findAllByMember(member));
    }

    @Override
    public GetRoleMemberResponse createRoleMember(PostRoleMemberRequest postRoleMemberRequest) {
        Organization organization = roleService.find(postRoleMemberRequest.getRole()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getOrganization();

        User user = memberService.find(postRoleMemberRequest.getMember()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getUser();

        String roleName = roleService.find(postRoleMemberRequest.getRole()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getName();

        if (!securityService.hasPermission(organization, Constants.PERMISSION_NAME_CAN_MANAGE_ROLES)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        roleMemberService.create(requestToRoleMemberFunction.apply(postRoleMemberRequest));

        keycloakController.addUserToKeycloakGroup(organization.getName(), user.getId(), roleName);

        return roleMemberService.findByMemberAndRole(
                    memberService.find(postRoleMemberRequest.getMember()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                    ),
                    roleService.find(postRoleMemberRequest.getRole()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                    ))
                .map(roleMemberToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetRoleMembersResponse createRoleMembers(PostRoleMembersRequest postRoleMembersRequest) {
        Organization organization = roleService.find(postRoleMembersRequest.getRoleId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getOrganization();

        if (!securityService.hasPermission(organization, Constants.PERMISSION_NAME_CAN_MANAGE_ROLES)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        roleMemberService.createAll(requestToRoleMembersFunction.apply(postRoleMembersRequest));

        return roleMembersToResponseFunction.apply(roleMemberService.findAllByRole(
                roleService.find(postRoleMembersRequest.getRoleId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                )
        ));
    }

    @Override
    public void deleteRoleMember(BigInteger id) {
        Organization organization = roleService.find(roleMemberService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getRole().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getOrganization();

        User user = roleMemberService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getMember().getUser();

        Role role = roleMemberService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getRole();

        if (!securityService.hasPermission(organization, Constants.PERMISSION_NAME_CAN_MANAGE_ROLES)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        keycloakController.removeUserFromKeycloakGroup(organization.getName(), user.getId(), role.getName());

        roleMemberService.find(id)
                .ifPresentOrElse(
                        roleMember -> roleMemberService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
