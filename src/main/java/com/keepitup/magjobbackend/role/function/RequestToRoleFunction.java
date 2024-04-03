package com.keepitup.magjobbackend.role.function;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.role.dto.PostRoleRequest;
import com.keepitup.magjobbackend.role.entity.Role;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToRoleFunction implements Function<PostRoleRequest, Role> {
    @Override
    public Role apply(PostRoleRequest request) {
        return Role.builder()
                .name(request.getName())
                .organization(Organization.builder()
                        .id(request.getOrganization())
                        .build())
                .build();
    }
}
