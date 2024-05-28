package com.keepitup.magjobbackend.organization.function;

import com.keepitup.magjobbackend.organization.dto.GetOrganizationsResponse;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class OrganizationsToResponseFunction implements BiFunction<Page<Organization>, Integer, GetOrganizationsResponse> {

    @Override
    public GetOrganizationsResponse apply(Page<Organization> entities, Integer count) {
        return GetOrganizationsResponse.builder()
                .organizations(entities.stream()
                        .map(organization -> GetOrganizationsResponse.Organization.builder()
                                .id(organization.getId())
                                .name(organization.getName())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
