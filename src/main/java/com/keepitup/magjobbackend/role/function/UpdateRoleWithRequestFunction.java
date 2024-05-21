package com.keepitup.magjobbackend.role.function;

import com.keepitup.magjobbackend.role.dto.PatchRoleRequest;
import com.keepitup.magjobbackend.role.entity.Role;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateRoleWithRequestFunction implements BiFunction<Role, PatchRoleRequest, Role> {
    @Override
    public Role apply(Role role, PatchRoleRequest request) {
        return Role.builder()
                .id(role.getId())
                .externalId(role.getExternalId())
                .name(request.getName())
                .organization(role.getOrganization())
                .canManageTasks(request.getCanManageTasks())
                .canManageRoles(request.getCanManageRoles())
                .canManageInvitations(request.getCanManageInvitations())
                .canManageAnnouncements(request.getCanManageAnnouncements())
                .build();
    }
}
