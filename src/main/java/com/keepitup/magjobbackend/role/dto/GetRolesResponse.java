package com.keepitup.magjobbackend.role.dto;

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
@Schema(description = "GetRolesResponse DTO")
public class GetRolesResponse {
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

        @Schema(description = "Role external Id")
        private String externalId;

        @Schema(description = "Organization id value")
        private BigInteger organizationId;
    }

    @Singular
    @Schema(description = "Role list")
    private List<Role> roles;
}
