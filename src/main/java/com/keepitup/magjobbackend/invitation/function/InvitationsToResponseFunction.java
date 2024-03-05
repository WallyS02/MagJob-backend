package com.keepitup.magjobbackend.invitation.function;

import com.keepitup.magjobbackend.invitation.dto.GetInvitationsResponse;
import com.keepitup.magjobbackend.invitation.entity.Invitation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class InvitationsToResponseFunction implements Function<List<Invitation>, GetInvitationsResponse> {
    @Override
    public GetInvitationsResponse apply(List<Invitation> entities) {
        return GetInvitationsResponse.builder()
                .invitations(entities.stream()
                        .map(invitation -> GetInvitationsResponse.Invitation.builder()
                                .userId(invitation.getUser().getId())
                                .organizationId(invitation.getOrganization().getId())
                                .organizationName(invitation.getOrganization().getName())
                                .build())
                        .toList())
                .build();
    }
}
