package com.keepitup.magjobbackend.organization.function;

import com.keepitup.magjobbackend.organization.dto.PatchOrganizationRequest;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.util.ImageUtil;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateOrganizationWithRequestFunction implements BiFunction<Organization, PatchOrganizationRequest, Organization> {
    @Override
    public Organization apply(Organization organization, PatchOrganizationRequest request) {
        return Organization.builder()
                .id(organization.getId())
                .name(request.getName())
                .banner(ImageUtil.compressImage(request.getBanner()))
                .dateOfCreation(organization.getDateOfCreation())
                .build();
    }
}
