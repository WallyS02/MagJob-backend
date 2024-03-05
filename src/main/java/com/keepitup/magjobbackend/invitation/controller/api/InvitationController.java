package com.keepitup.magjobbackend.invitation.controller.api;

import com.keepitup.magjobbackend.invitation.dto.AcceptInvitationRequest;
import com.keepitup.magjobbackend.invitation.dto.GetInvitationResponse;
import com.keepitup.magjobbackend.invitation.dto.GetInvitationsResponse;
import com.keepitup.magjobbackend.invitation.dto.PostInvitationRequest;
import com.keepitup.magjobbackend.member.dto.GetMemberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

public interface InvitationController {

    @GetMapping("/api/invitations/{userId}/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetInvitationResponse getInvitation(
            @PathVariable("userId")
            BigInteger userId,
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @GetMapping("/api/users/{userId}/invitations")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetInvitationsResponse getInvitationsByUser(
            @PathVariable("userId")
            BigInteger  userId
    );

    @GetMapping("/api/organizations/{organizationId}/invitations")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetInvitationsResponse getInvitationsByOrganization(
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @PostMapping("/api/invitations")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetInvitationResponse sendInvitation(
            @RequestBody
            PostInvitationRequest request
    );

    @DeleteMapping("/api/invitations/{userId}/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInvitation(
            @PathVariable("userId")
            BigInteger userId,
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @PostMapping("/api/invitations/accept")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMemberResponse acceptInvitation(
            @RequestBody
            AcceptInvitationRequest request
    );

    @PostMapping("/api/invitations/reject")
    @ResponseStatus(HttpStatus.OK)
    void rejectInvitation(
            @RequestBody
            PostInvitationRequest request
    );

}
