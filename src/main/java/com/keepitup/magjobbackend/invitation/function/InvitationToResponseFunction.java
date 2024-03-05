package com.keepitup.magjobbackend.invitation.function;

import com.keepitup.magjobbackend.invitation.dto.GetInvitationResponse;
import com.keepitup.magjobbackend.invitation.entity.Invitation;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class InvitationToResponseFunction implements Function<Invitation, GetInvitationResponse> {
    @Override
    public GetInvitationResponse apply(Invitation invitation) {
        return GetInvitationResponse.builder()
                .isActive(invitation.getIsActive())
                .dateOfCreation(invitation.getDateOfCreation())
                .user(GetInvitationResponse.User.builder()
                        .id(invitation.getUser().getId())
                        .email(invitation.getUser().getEmail())
                        .build())
                .organization(GetInvitationResponse.Organization.builder()
                        .id(invitation.getOrganization().getId())
                        .name(invitation.getOrganization().getName())
                        .build())
                .build();
    }
}
