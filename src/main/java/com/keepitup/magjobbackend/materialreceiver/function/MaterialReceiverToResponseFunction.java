package com.keepitup.magjobbackend.materialreceiver.function;

import com.keepitup.magjobbackend.materialreceiver.dto.GetMaterialReceiverResponse;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;

import java.util.function.Function;

public class MaterialReceiverToResponseFunction implements Function<MaterialReceiver, GetMaterialReceiverResponse> {
    @Override
    public GetMaterialReceiverResponse apply(MaterialReceiver materialReceiver) {
        return GetMaterialReceiverResponse.builder()
                .id(materialReceiver.getId())
                .member(GetMaterialReceiverResponse.Member.builder()
                        .id(materialReceiver.getMember().getId())
                        .pseudonym(materialReceiver.getMember().getPseudonym())
                        .build())
                .material(GetMaterialReceiverResponse.Material.builder()
                        .id(materialReceiver.getMaterial().getId())
                        .title(materialReceiver.getMaterial().getTitle())
                        .build())
                .build();
    }
}
