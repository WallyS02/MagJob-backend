package com.keepitup.magjobbackend.material.function;

import com.keepitup.magjobbackend.material.dto.GetMaterialsResponse;
import com.keepitup.magjobbackend.material.entity.Material;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class MaterialsToResponseFunction implements Function<List<Material>, GetMaterialsResponse> {
    @Override
    public GetMaterialsResponse apply(List<Material> materials) {
        return GetMaterialsResponse.builder()
                .materials(materials.stream()
                        .map(material -> GetMaterialsResponse.Material.builder()
                                .id(material.getId())
                                .title(material.getTitle())
                                .description(material.getDescription())
                                .organizationId(material.getOrganization().getId())
                                .build())
                        .toList())
                .build();
    }
}
