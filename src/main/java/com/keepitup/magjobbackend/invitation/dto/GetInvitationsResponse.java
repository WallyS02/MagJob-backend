package com.keepitup.magjobbackend.invitation.dto;


import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetInvitationsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Invitation {

        private BigInteger userId;
        private BigInteger organizationId;

        private String organizationName;

    }

    @Singular
    private List<Invitation> invitations;
}
