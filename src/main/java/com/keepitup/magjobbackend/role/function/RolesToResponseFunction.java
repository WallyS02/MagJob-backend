package com.keepitup.magjobbackend.role.function;

import com.keepitup.magjobbackend.role.dto.GetRolesResponse;
import com.keepitup.magjobbackend.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class RolesToResponseFunction implements BiFunction<Page<Role>, Integer, GetRolesResponse> {
    @Override
    public GetRolesResponse apply(Page<Role> roles, Integer count) {
        return GetRolesResponse.builder()
                .roles(roles.stream()
                        .map(role -> GetRolesResponse.Role.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .externalId(role.getExternalId())
                                .organizationId(role.getOrganization().getId())
                                .canManageRoles(role.getCanManageRoles())
                                .canManageTasks(role.getCanManageTasks())
                                .canManageInvitations(role.getCanManageInvitations())
                                .canManageAnnouncements(role.getCanManageAnnouncements())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
