package com.keepitup.magjobbackend.role.dto;

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
@Schema(description = "GetRoleResponse DTO")
public class GetRoleResponse {
    @Schema(description = "Role id value")
    private BigInteger id;

    @Schema(description = "Role name")
    private String name;

    @Schema(description = "Role external Id")
    private String externalId;

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

        @Schema(description = "Organization name")
        private String name;

    }

    @Schema(description = "Organization class value")
    private Organization organization;
}
