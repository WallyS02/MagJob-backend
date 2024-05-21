package com.keepitup.magjobbackend.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PatchRoleRequest DTO")
public class PatchRoleRequest {
    @Schema(description = "Role name")
    private String name;

    @Schema(description = "Permission to Manage Tasks")
    private Boolean canManageTasks;

    @Schema(description = "Permission to Manage Announcements")
    private Boolean canManageAnnouncements;

    @Schema(description = "Permission to Manage Invitations")
    private Boolean canManageInvitations;

    @Schema(description = "Permission to Manage Roles")
    private Boolean canManageRoles;
}
