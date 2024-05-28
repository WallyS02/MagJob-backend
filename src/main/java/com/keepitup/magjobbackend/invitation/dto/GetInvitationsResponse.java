package com.keepitup.magjobbackend.invitation.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "GetInvitationsResponse DTO")
public class GetInvitationsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Invitation {

        @Schema(description = "User id value")
        private BigInteger userId;

        @Schema(description = "Organization id value")
        private BigInteger organizationId;

        @Schema(description = "Organization name value")
        private String organizationName;

    }

    @Singular
    @Schema(description = "Invitation list")
    private List<Invitation> invitations;

    @Schema(description = "Number of all objects")
    private Integer count;
}