package com.keepitup.magjobbackend.material.function;

import com.keepitup.magjobbackend.material.dto.PostMaterialRequest;
import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.function.Function;

@Component
public class RequestToMaterialFunction implements Function<PostMaterialRequest, Material> {
    @Override
    public Material apply(PostMaterialRequest request) {
        return Material.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .contentType(request.getContentType())
                .size(request.getSize())
                .content(request.getContent())
                .dateOfCreation(ZonedDateTime.now())
                .organization(Organization.builder()
                        .id(request.getOrganization())
                        .build())
                .build();
    }
}
