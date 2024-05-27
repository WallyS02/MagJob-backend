package com.keepitup.magjobbackend.invitation.controller.api;

import com.keepitup.magjobbackend.invitation.dto.AcceptInvitationRequest;
import com.keepitup.magjobbackend.invitation.dto.GetInvitationResponse;
import com.keepitup.magjobbackend.invitation.dto.GetInvitationsResponse;
import com.keepitup.magjobbackend.invitation.dto.PostInvitationRequest;
import com.keepitup.magjobbackend.member.dto.GetMemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name = "Invitation Controller")
public interface InvitationController {

    @Operation(summary = "Get Invitation")
    @GetMapping("/api/invitations/{userId}/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetInvitationResponse getInvitation(
            @Parameter(
                    name = "userId",
                    description = "User id value",
                    required = true
            )
            @PathVariable("userId")
            BigInteger userId,
            @Parameter(
                    name = "organizationId",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @Operation(summary = "Get Invitations By User")
    @GetMapping("/api/users/{userId}/invitations")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetInvitationsResponse getInvitationsByUser(
            @Parameter(
                    name = "page number",
                    description = "Page number to retrieve"
            )
            @RequestParam(defaultValue = "#{pageConfig.number}")
            int page,
            @Parameter(
                    name = "page size",
                    description = "Number of records per page"
            )
            @RequestParam(defaultValue = "#{pageConfig.size}")
            int size,
            @Parameter(
                    name = "userId",
                    description = "User id value",
                    required = true
            )
            @PathVariable("userId")
            BigInteger  userId
    );

    @Operation(summary = "Get Invitations By Organization")
    @GetMapping("/api/organizations/{organizationId}/invitations")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetInvitationsResponse getInvitationsByOrganization(
            @Parameter(
                    name = "page number",
                    description = "Page number to retrieve"
            )
            @RequestParam(defaultValue = "#{pageConfig.number}")
            int page,
            @Parameter(
                    name = "page size",
                    description = "Number of records per page"
            )
            @RequestParam(defaultValue = "#{pageConfig.size}")
            int size,
            @Parameter(
                    name = "organizationId",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @Operation(summary = "Send Invitation")
    @PostMapping("/api/invitations")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetInvitationResponse sendInvitation(
            @Parameter(
                    name = "PostInvitationRequest",
                    description = "PostInvitationRequest DTO",
                    schema = @Schema(implementation = PostInvitationRequest.class),
                    required = true
            )
            @RequestBody
            PostInvitationRequest request
    );

    @Operation(summary = "Delete Invitation")
    @DeleteMapping("/api/invitations/{userId}/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInvitation(
            @Parameter(
                    name = "userId",
                    description = "User id value",
                    required = true
            )
            @PathVariable("userId")
            BigInteger userId,
            @Parameter(
                    name = "organizationId",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @Operation(summary = "Accept Invitation")
    @PostMapping("/api/invitations/accept")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMemberResponse acceptInvitation(
            @Parameter(
                    name = "AcceptInvitationRequest",
                    description = "AcceptInvitationRequest DTO",
                    schema = @Schema(implementation = AcceptInvitationRequest.class),
                    required = true
            )
            @RequestBody
            AcceptInvitationRequest request
    );

    @Operation(summary = "Reject Invitation")
    @PostMapping("/api/invitations/reject")
    @ResponseStatus(HttpStatus.OK)
    void rejectInvitation(
            @Parameter(
                    name = "PostInvitationRequest",
                    description = "PostInvitationRequest DTO",
                    schema = @Schema(implementation = PostInvitationRequest.class),
                    required = true
            )
            @RequestBody
            PostInvitationRequest request
    );

}
