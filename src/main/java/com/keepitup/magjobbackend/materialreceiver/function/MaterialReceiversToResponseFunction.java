package com.keepitup.magjobbackend.materialreceiver.function;

import com.keepitup.magjobbackend.materialreceiver.dto.GetMaterialReceiversResponse;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class MaterialReceiversToResponseFunction implements Function<List<MaterialReceiver>, GetMaterialReceiversResponse> {
    @Override
    public GetMaterialReceiversResponse apply(List<MaterialReceiver> materialReceivers) {
        return GetMaterialReceiversResponse.builder()
                .materialReceivers(materialReceivers.stream()
                        .map(materialReceiver -> GetMaterialReceiversResponse.MaterialReceiver.builder()
                                .id(materialReceiver.getId())
                                .memberId(materialReceiver.getMember().getId())
                                .materialId(materialReceiver.getMaterial().getId())
                                .build())
                        .toList())
                .build();
    }
}
