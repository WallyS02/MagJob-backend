package com.keepitup.magjobbackend.rolemember.dto;

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
@Schema(description = "GetRoleMemberResponse DTO")
public class GetRoleMemberResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member {

        @Schema(description = "Member id value")
        private BigInteger id;

        @Schema(description = "Member pseudonym")
        private String pseudonym;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Role {
        @Schema(description = "Role id value")
        private BigInteger id;

        @Schema(description = "Role name")
        private String name;
    }

    @Schema(description = "Role member id value")
    private BigInteger id;

    @Schema(description = "Member class value")
    private Member member;

    @Schema(description = "Role class value")
    private Role role;

}
