package com.keepitup.magjobbackend.role.function;

import com.keepitup.magjobbackend.role.dto.GetRolesByOrganizationResponse;
import com.keepitup.magjobbackend.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class RolesByOrganizationToResponseFunction implements BiFunction<Page<Role>, Integer, GetRolesByOrganizationResponse> {
    @Override
    public GetRolesByOrganizationResponse apply(Page<Role> roles, Integer count) {
        return GetRolesByOrganizationResponse.builder()
                .roles(roles.stream()
                        .map(role -> GetRolesByOrganizationResponse.Role.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .externalId(role.getExternalId())
                                .organizationId(role.getOrganization().getId())
                                .canManageRoles(role.getCanManageRoles())
                                .canManageTasks(role.getCanManageTasks())
                                .canManageInvitations(role.getCanManageInvitations())
                                .canManageAnnouncements(role.getCanManageAnnouncements())
                                .members(role.getRoleMembers().stream()
                                        .map(member -> GetRolesByOrganizationResponse.Member.builder()
                                                .id(member.getMember().getId())
                                                .firstName(member.getMember().getUser().getFirstname())
                                                .lastName(member.getMember().getUser().getLastname())
                                                .build())
                                        .toList())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
