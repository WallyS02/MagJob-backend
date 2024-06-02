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

    @Schema(description = "Permission to Manage Tasks")
    private Boolean canManageTasks;

    @Schema(description = "Permission to Manage Announcements")
    private Boolean canManageAnnouncements;

    @Schema(description = "Permission to Manage Invitations")
    private Boolean canManageInvitations;

    @Schema(description = "Permission to Manage Roles")
    private Boolean canManageRoles;

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
