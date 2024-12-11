package com.keepitup.magjobbackend.organization.controller.impl;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chat.service.api.ChatService;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.chatmember.service.api.ChatMemberService;
import com.keepitup.magjobbackend.configuration.Constants;
import com.keepitup.magjobbackend.configuration.KeycloakController;
import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.jwt.CustomJwt;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.notification.service.impl.NotificationDefaultService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final NotificationDefaultService notificationService;
    private final ChatService chatService;
    private final ChatMemberService chatMemberService;
    private final KeycloakController keycloakController;
    private final SecurityService securityService;

    @Autowired
    public OrganizationDefaultController(
            OrganizationService service,
            MemberService memberService,
            UserService userService,
            RoleService roleService,
            RoleMemberService roleMemberService,
            NotificationDefaultService notificationService,
            OrganizationsToResponseFunction organizationsToResponse,
            OrganizationToResponseFunction organizationToResponse,
            RequestToOrganizationFunction requestToOrganization,
            UpdateOrganizationWithRequestFunction updateOrganizationWithRequest,
            ChatService chatService,
            ChatMemberService chatMemberService,
            KeycloakController keycloakController,
            SecurityService securityService
    ) {
        this.service = service;
        this.memberService = memberService;
        this.userService = userService;
        this.roleService = roleService;
        this.roleMemberService = roleMemberService;
        this.notificationService = notificationService;
        this.organizationsToResponse = organizationsToResponse;
        this.organizationToResponse = organizationToResponse;
        this.requestToOrganization = requestToOrganization;
        this.updateOrganizationWithRequest = updateOrganizationWithRequest;
        this.chatService = chatService;
        this.chatMemberService = chatMemberService;
        this.keycloakController = keycloakController;
        this.securityService = securityService;
    }

    @Override
    public GetOrganizationsResponse getOrganizations(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = service.findAll().size();
        return organizationsToResponse.apply(service.findAll(pageRequest), count);
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
    public GetOrganizationsResponse getOrganizationsByUser(int page, int size, UUID userId) {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        UUID loggedInUserId = UUID.fromString(jwt.getExternalId());

        if (!loggedInUserId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
  
        PageRequest pageRequest = PageRequest.of(page, size);

        Optional<Page<Organization>> countOptional = memberService.findAllOrganizationsByUser(userId, Pageable.unpaged());
        Integer count = countOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getNumberOfElements();

        Optional<Page<Organization>> organizationsOptional = memberService.findAllOrganizationsByUser(userId, pageRequest);

        Page<Organization> organizations = organizationsOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return organizationsToResponse.apply(organizations, count);
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

                roleService.create(Role.builder()
                        .name(Constants.ROLE_NAME_OWNER)
                        .organization(createdOrganization.get())
                        .externalId(roleName2groupExternalId.get(Constants.ROLE_NAME_OWNER))
                        .canManageTasks(true)
                        .canManageRoles(true)
                        .canManageInvitations(true)
                        .canManageAnnouncements(true)
                        .isAdmin(true) // TODO delete in future; For testing purposes Owner will be also Admin
                        .build());

                roleService.create(Role.builder()
                        .name(Constants.ROLE_NAME_MODERATOR)
                        .organization(createdOrganization.get())
                        .externalId(roleName2groupExternalId.get(Constants.ROLE_NAME_MODERATOR))
                        .canManageTasks(true)
                        .canManageRoles(false)
                        .canManageInvitations(true)
                        .canManageAnnouncements(true)
                        .build());

                roleService.create(Role.builder()
                        .name(Constants.ROLE_NAME_MEMBER)
                        .organization(createdOrganization.get())
                        .externalId(roleName2groupExternalId.get(Constants.ROLE_NAME_MEMBER))
                        .canManageTasks(false)
                        .canManageRoles(false)
                        .canManageInvitations(false)
                        .canManageAnnouncements(false)
                        .build());


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

                    createInitialChat(createdOrganization.get(), ownerMember.get());
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

        List<User> users = memberService.findAllUsersByOrganization(id, Pageable.unpaged()).get().stream().toList();

        service.delete(id);

        users.forEach(user -> notificationService.create(Notification.builder()
                .user(user)
                .content(String.format(Constants.NOTIFICATION_ORGANIZATION_DELETION_TEMPLATE, organization.get().getName()))
                .build()));
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

        notificationService.create(Notification.builder()
                .organization(organization.get())
                .content(String.format(Constants.NOTIFICATION_ORGANIZATION_UPDATE_TEMPLATE, organization.get().getName()))
                .build());

        return getOrganization(id);
    }

    private void createInitialChat(Organization organization, Member admin) {
        String chatName = "Main chat - " + organization.getName();

        chatService.create(Chat.builder()
                .title(chatName)
                .organization(organization)
                .build());
        Optional<Chat> createdChat = chatService.findByTitle(chatName);

        if (createdChat.isPresent()) {
            chatMemberService.acceptInvitation(ChatMember.builder()
                    .chat(createdChat.get())
                    .nickname(admin.getPseudonym())
                    .isInvitationAccepted(true)
                    .member(admin)
                    .build());

            ChatMember adminChatMember = chatMemberService.findByMemberAndChat(admin, createdChat.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            chatService.addAdmin(createdChat.get(), adminChatMember);
        }
    }
}
