package com.keepitup.magjobbackend.invitation.controller.impl;

import com.keepitup.magjobbackend.invitation.controller.api.InvitationController;
import com.keepitup.magjobbackend.invitation.dto.AcceptInvitationRequest;
import com.keepitup.magjobbackend.invitation.dto.GetInvitationResponse;
import com.keepitup.magjobbackend.invitation.dto.GetInvitationsResponse;
import com.keepitup.magjobbackend.invitation.dto.PostInvitationRequest;
import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.invitation.function.InvitationToResponseFunction;
import com.keepitup.magjobbackend.invitation.function.InvitationsToResponseFunction;
import com.keepitup.magjobbackend.invitation.function.RequestToInvitationFunction;
import com.keepitup.magjobbackend.invitation.service.api.InvitationService;
import com.keepitup.magjobbackend.member.dto.GetMemberResponse;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.function.MemberToResponseFunction;
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
import java.util.Optional;

@RestController
@Log
public class InvitationDefaultController implements InvitationController {

    private final InvitationService service;
    private final InvitationsToResponseFunction invitationsToResponse;
    private final InvitationToResponseFunction invitationToResponse;
    private final RequestToInvitationFunction requestToInvitation;
    private final MemberService memberService;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final MemberToResponseFunction memberToResponse;


    @Autowired
    public InvitationDefaultController(InvitationService service,
                                       InvitationsToResponseFunction invitationsToResponse,
                                       InvitationToResponseFunction invitationToResponse,
                                       RequestToInvitationFunction requestToInvitation,
                                       MemberService memberService,
                                       UserService userService,
                                       OrganizationService organizationService,
                                       MemberToResponseFunction memberToResponse) {
        this.service = service;
        this.invitationsToResponse = invitationsToResponse;
        this.invitationToResponse = invitationToResponse;
        this.requestToInvitation = requestToInvitation;
        this.memberService = memberService;
        this.userService = userService;
        this.organizationService = organizationService;
        this.memberToResponse = memberToResponse;
    }


    @Override
    public GetInvitationResponse getInvitation(String userExternalId, BigInteger organizationId) {
        return service.findByUserExternalIdAndOrganization(userExternalId, organizationId)
                .map(invitationToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetInvitationsResponse getInvitationsByUser(String userExternalId) {
        return service.findAllByUserAndIsActive(userExternalId, true)
                .map(invitationsToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetInvitationsResponse getInvitationsByOrganization(BigInteger organizationId) {
        return service.findAllByOrganizationAndIsActive(organizationId, true)
                .map(invitationsToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetInvitationResponse sendInvitation(PostInvitationRequest request) {
        Optional<Invitation> invitation = service.findByUserAndOrganization(request.getUser(), request.getOrganization());
        if (invitation.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        else {
            if (memberService.findByUserAndOrganization(userService.find(request.getUser()).get(),
                    organizationService.find(request.getOrganization()).get()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            } else {
                service.create(requestToInvitation.apply(request));
            }
        }

        return service.findByUserAndOrganization(request.getUser(), request.getOrganization())
                .map(invitationToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteInvitation(String userExternalId, BigInteger organizationId) {
        service.findByUserAndOrganization(userExternalId, organizationId)
                .ifPresentOrElse(
                        invitation -> service.delete(userExternalId, organizationId),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public GetMemberResponse acceptInvitation(AcceptInvitationRequest request) {
        Optional<Invitation> invitation = service.findByUserAndOrganization(request.getUser(), request.getOrganization());
        Optional<User> user = userService.find(request.getUser());
        Optional<Organization> organization = organizationService.find(request.getOrganization());

        if (invitation.isPresent()) {

            if (user.isPresent() && organization.isPresent()) {
                memberService.create(Member.builder()
                        .pseudonym(request.getPseudonym())
                        .organization(organization.get())
                        .user(user.get())
                        .build());

                service.delete(user.get().getId(), organization.get().getId());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return memberToResponse.apply(memberService.findByUserAndOrganization(user.get(), organization.get()).get());
    }

    @Override
    public void rejectInvitation(PostInvitationRequest request) {

        Optional<Invitation> invitation = service.findByUserAndOrganization(request.getUser(), request.getOrganization());
        if (invitation.isPresent()) {
            service.delete(request.getUser(), request.getOrganization());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
