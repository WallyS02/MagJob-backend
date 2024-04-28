package com.keepitup.magjobbackend.material.function;

import com.keepitup.magjobbackend.material.dto.PatchMaterialRequest;
import com.keepitup.magjobbackend.material.entity.Material;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.function.BiFunction;

@Component
public class UpdateMaterialWithRequestFunction implements BiFunction<Material, PatchMaterialRequest, Material> {
    @Override
    public Material apply(Material material, PatchMaterialRequest request) {
        return Material.builder()
                .id(material.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .contentType(request.getContentType())
                .size(request.getSize())
                .content(request.getContent())
                .organization(material.getOrganization())
                .build();
    }
}
