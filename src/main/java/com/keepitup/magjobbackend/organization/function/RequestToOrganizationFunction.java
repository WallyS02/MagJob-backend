package com.keepitup.magjobbackend.organization.function;

import com.keepitup.magjobbackend.organization.dto.PostOrganizationRequest;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.util.ImageUtil;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.function.Function;

@Component
public class RequestToOrganizationFunction implements Function<PostOrganizationRequest, Organization> {
    @Override
    public Organization apply(PostOrganizationRequest request) {
        return Organization.builder()
                .name(request.getName())
                .banner(ImageUtil.compressImage(request.getBanner()))
                .build();
    }
}
