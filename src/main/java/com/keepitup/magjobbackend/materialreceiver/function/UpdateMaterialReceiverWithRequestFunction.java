package com.keepitup.magjobbackend.materialreceiver.function;

import com.keepitup.magjobbackend.materialreceiver.dto.PatchMaterialReceiverRequest;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateMaterialReceiverWithRequestFunction implements BiFunction<MaterialReceiver, PatchMaterialReceiverRequest, MaterialReceiver> {
    @Override
    public MaterialReceiver apply(MaterialReceiver materialReceiver, PatchMaterialReceiverRequest request) {
        return MaterialReceiver.builder()
                .id(materialReceiver.getId())
                .member(materialReceiver.getMember())
                .material(materialReceiver.getMaterial())
                .build();
    }
}
