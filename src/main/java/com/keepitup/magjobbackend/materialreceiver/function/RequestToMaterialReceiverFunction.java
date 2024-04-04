package com.keepitup.magjobbackend.materialreceiver.function;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.dto.PostMaterialReceiverRequest;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToMaterialReceiverFunction implements Function<PostMaterialReceiverRequest, MaterialReceiver> {
    @Override
    public MaterialReceiver apply(PostMaterialReceiverRequest request) {
        return MaterialReceiver.builder()
                .member(Member.builder()
                        .id(request.getMember())
                        .build())
                .material(Material.builder()
                        .id(request.getMaterial())
                        .build())
                .build();
    }
}
