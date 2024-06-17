package com.keepitup.magjobbackend.materialreceiver.function;

import com.keepitup.magjobbackend.materialreceiver.dto.GetMaterialReceiversResponse;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class MaterialReceiversToResponseFunction implements BiFunction<Page<MaterialReceiver>, Integer, GetMaterialReceiversResponse> {
    @Override
    public GetMaterialReceiversResponse apply(Page<MaterialReceiver> materialReceivers, Integer count) {
        return GetMaterialReceiversResponse.builder()
                .materialReceivers(materialReceivers.stream()
                        .map(materialReceiver -> GetMaterialReceiversResponse.MaterialReceiver.builder()
                                .id(materialReceiver.getId())
                                .memberId(materialReceiver.getMember().getId())
                                .materialId(materialReceiver.getMaterial().getId())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
