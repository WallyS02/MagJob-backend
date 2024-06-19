package com.keepitup.magjobbackend.invitation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetInvitationResponse DTO")
public class GetInvitationResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {

        @Schema(description = "User id value")
        private UUID id;

        @Schema(description = "User email value")
        private String email;

    }
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Organization {

        @Schema(description = "Organization id value")
        private BigInteger id;

        @Schema(description = "Organization name value")
        private String name;

    }

    @Schema(description = "Invitation isActive value")
    private Boolean isActive;

    @Schema(description = "Invitation date of creation value")
    private ZonedDateTime dateOfCreation;

    @Schema(description = "Organization class value")
    private Organization organization;

    @Schema(description = "User class value")
    private User user;

}