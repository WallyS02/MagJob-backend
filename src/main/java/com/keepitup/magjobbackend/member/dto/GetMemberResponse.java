package com.keepitup.magjobbackend.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetMemberResponse DTO")
public class GetMemberResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {

        @Schema(description = "User external id  value")
        private String externalId;

        @Schema(description = "User email  value")
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


    @Schema(description = "Member id value")
    private BigInteger id;

    @Schema(description = "Member pseudonym value")
    private String pseudonym;

    @Schema(description = "Member isStillMember value")
    private Boolean isStillMember;

    @Schema(description = "Organization class value")
    private Organization organization;

    @Schema(description = "User class value")
    private User user;

}