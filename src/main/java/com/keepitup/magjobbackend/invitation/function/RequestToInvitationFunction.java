package com.keepitup.magjobbackend.invitation.function;

import com.keepitup.magjobbackend.invitation.dto.PostInvitationRequest;
import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToInvitationFunction implements Function<PostInvitationRequest, Invitation> {
    @Override
    public Invitation apply(PostInvitationRequest request) {
        return Invitation.builder()
                .user(User.builder()
                        .externalId(request.getUser())
                        .build())
                .organization(Organization.builder()
                        .id(request.getOrganization())
                        .build())
                .build();
    }
}
