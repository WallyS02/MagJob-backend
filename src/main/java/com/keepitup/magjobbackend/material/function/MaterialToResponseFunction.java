package com.keepitup.magjobbackend.material.function;

import com.keepitup.magjobbackend.material.dto.GetMaterialResponse;
import com.keepitup.magjobbackend.material.entity.Material;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MaterialToResponseFunction implements Function<Material, GetMaterialResponse> {
    @Override
    public GetMaterialResponse apply(Material material) {
        return GetMaterialResponse.builder()
                .id(material.getId())
                .title(material.getTitle())
                .description(material.getDescription())
                .contentType(material.getContentType())
                .size(material.getSize())
                .dateOfCreation(material.getDateOfCreation())
                .content(material.getContent())
                .organization(GetMaterialResponse.Organization.builder()
                        .id(material.getOrganization().getId())
                        .name(material.getOrganization().getName())
                        .build())
                .build();
    }
}
