package com.keepitup.magjobbackend.material.function;

import com.keepitup.magjobbackend.material.dto.GetMaterialsResponse;
import com.keepitup.magjobbackend.material.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class MaterialsToResponseFunction implements BiFunction<Page<Material>, Integer, GetMaterialsResponse> {
    @Override
    public GetMaterialsResponse apply(Page<Material> materials, Integer count) {
        return GetMaterialsResponse.builder()
                .materials(materials.stream()
                        .map(material -> GetMaterialsResponse.Material.builder()
                                .id(material.getId())
                                .title(material.getTitle())
                                .description(material.getDescription())
                                .organizationId(material.getOrganization().getId())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
