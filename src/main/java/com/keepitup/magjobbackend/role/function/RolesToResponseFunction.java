package com.keepitup.magjobbackend.role.function;

import com.keepitup.magjobbackend.role.dto.GetRolesResponse;
import com.keepitup.magjobbackend.role.entity.Role;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.List;

@Component
public class RolesToResponseFunction implements Function<List<Role>, GetRolesResponse> {
    @Override
    public GetRolesResponse apply(List<Role> roles) {
        return GetRolesResponse.builder()
                .roles(roles.stream()
                        .map(role -> GetRolesResponse.Role.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .externalId(role.getExternalId())
                                .organizationId(role.getOrganization().getId())
                                .build())
                        .toList())
                .build();
    }
}
