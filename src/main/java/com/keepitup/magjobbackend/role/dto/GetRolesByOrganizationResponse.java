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
@Schema(description = "GetMembersByOrganizationResponse DTO")
public class GetRolesByOrganizationResponse {

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

        @Schema(description = "Member first name")
        private String firstName;

        @Schema(description = "Member last name")
        private String lastName;
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

        @Schema(description = "Role external Id")
        private String externalId;

        @Schema(description = "Organization id value")
        private BigInteger organizationId;

        @Schema(description = "Permission to Manage Tasks")
        private Boolean canManageTasks;

        @Schema(description = "Permission to Manage Announcements")
        private Boolean canManageAnnouncements;

        @Schema(description = "Permission to Manage Invitations")
        private Boolean canManageInvitations;

        @Schema(description = "Permission to Manage Roles")
        private Boolean canManageRoles;

        @Singular
        @Schema(description = "Member list")
        private List<Member> members;
    }

    @Singular
    @Schema(description = "Role list")
    private List<Role> roles;
}
