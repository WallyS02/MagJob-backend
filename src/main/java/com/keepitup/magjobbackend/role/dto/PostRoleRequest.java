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
@Schema(description = "PostRoleRequest DTO")
public class PostRoleRequest {

    @Schema(description = "Role name")
    private String name;

    @Schema(description = "Organization id value")
    private BigInteger organization;

    @Schema(description = "Permission to Manage Tasks")
    private Boolean canManageTasks;

    @Schema(description = "Permission to Manage Announcements")
    private Boolean canManageAnnouncements;

    @Schema(description = "Permission to Manage Invitations")
    private Boolean canManageInvitations;

    @Schema(description = "Permission to Manage Roles")
    private Boolean canManageRoles;
}
