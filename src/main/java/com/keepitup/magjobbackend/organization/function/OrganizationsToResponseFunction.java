package com.keepitup.magjobbackend.organization.function;

import com.keepitup.magjobbackend.organization.dto.GetOrganizationsResponse;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class OrganizationsToResponseFunction implements Function<List<Organization>, GetOrganizationsResponse>{

    @Override
    public GetOrganizationsResponse apply(List<Organization> entities) {
        return GetOrganizationsResponse.builder()
                .organizations(entities.stream()
                        .map(organization -> GetOrganizationsResponse.Organization.builder()
                                .id(organization.getId())
                                .name(organization.getName())
                                .build())
                        .toList())
                .build();
    }
}
