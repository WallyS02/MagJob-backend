package com.keepitup.magjobbackend.role.function;

import com.keepitup.magjobbackend.role.dto.GetRolesByOrganizationResponse;
import com.keepitup.magjobbackend.role.dto.GetRolesResponse;
import com.keepitup.magjobbackend.role.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RolesByOrganizationToResponseFunction implements Function<List<Role>, GetRolesByOrganizationResponse> {
    @Override
    public GetRolesByOrganizationResponse apply(List<Role> roles) {
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
                .build();
    }
}
