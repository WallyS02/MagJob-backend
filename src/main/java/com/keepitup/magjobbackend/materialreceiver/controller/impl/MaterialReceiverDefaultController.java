package com.keepitup.magjobbackend.materialreceiver.controller.impl;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.material.service.impl.MaterialDefaultService;
import com.keepitup.magjobbackend.materialreceiver.controller.api.MaterialReceiverController;
import com.keepitup.magjobbackend.materialreceiver.dto.GetMaterialReceiverResponse;
import com.keepitup.magjobbackend.materialreceiver.dto.GetMaterialReceiversResponse;
import com.keepitup.magjobbackend.materialreceiver.dto.PatchMaterialReceiverRequest;
import com.keepitup.magjobbackend.materialreceiver.dto.PostMaterialReceiverRequest;
import com.keepitup.magjobbackend.materialreceiver.function.MaterialReceiverToResponseFunction;
import com.keepitup.magjobbackend.materialreceiver.function.MaterialReceiversToResponseFunction;
import com.keepitup.magjobbackend.materialreceiver.function.RequestToMaterialReceiverFunction;
import com.keepitup.magjobbackend.materialreceiver.function.UpdateMaterialReceiverWithRequestFunction;
import com.keepitup.magjobbackend.materialreceiver.service.impl.MaterialReceiverDefaultService;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.impl.MemberDefaultService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@RestController
@Log
public class MaterialReceiverDefaultController implements MaterialReceiverController {
    private final MaterialReceiverDefaultService materialReceiverService;
    private final MaterialDefaultService materialService;
    private final MemberDefaultService memberService;
    private final MaterialReceiverToResponseFunction materialReceiverToResponseFunction;
    private final MaterialReceiversToResponseFunction materialReceiversToResponseFunction;
    private final RequestToMaterialReceiverFunction requestToMaterialReceiverFunction;
    private final UpdateMaterialReceiverWithRequestFunction updateMaterialReceiverWithRequestFunction;

    public MaterialReceiverDefaultController(
            MaterialReceiverDefaultService materialReceiverService,
            MaterialDefaultService materialService,
            MemberDefaultService memberService,
            MaterialReceiverToResponseFunction materialReceiverToResponseFunction,
            MaterialReceiversToResponseFunction materialReceiversToResponseFunction,
            RequestToMaterialReceiverFunction requestToMaterialReceiverFunction,
            UpdateMaterialReceiverWithRequestFunction updateMaterialReceiverWithRequestFunction
    ) {
        this.materialReceiverService = materialReceiverService;
        this.materialService = materialService;
        this.memberService = memberService;
        this.materialReceiverToResponseFunction = materialReceiverToResponseFunction;
        this.materialReceiversToResponseFunction = materialReceiversToResponseFunction;
        this.requestToMaterialReceiverFunction = requestToMaterialReceiverFunction;
        this.updateMaterialReceiverWithRequestFunction = updateMaterialReceiverWithRequestFunction;
    }

    @Override
    public GetMaterialReceiversResponse getMaterialReceivers() {
        return materialReceiversToResponseFunction.apply(materialReceiverService.findAll());
    }

    @Override
    public GetMaterialReceiverResponse getMaterialReceiver(BigInteger id) {
        return materialReceiverService.find(id)
                .map(materialReceiverToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMaterialReceiversResponse getMaterialReceiversByMaterial(BigInteger materialId) {
        Optional<Material> materialOptional = materialService.find(materialId);

        Material material = materialOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return materialReceiversToResponseFunction.apply(materialReceiverService.findAllByMaterial(material));
    }

    @Override
    public GetMaterialReceiversResponse getMaterialReceiversByMember(BigInteger memberId) {
        Optional<Member> memberOptional = memberService.find(memberId);

        Member member = memberOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return materialReceiversToResponseFunction.apply(materialReceiverService.findAllByMember(member));
    }

    @Override
    public GetMaterialReceiverResponse createMaterialReceiver(PostMaterialReceiverRequest postMaterialReceiverRequest) {
        materialReceiverService.create(requestToMaterialReceiverFunction.apply(postMaterialReceiverRequest));
        return materialReceiverService.findByMemberAndMaterial(
                        memberService.find(postMaterialReceiverRequest.getMember()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                        materialService.find(postMaterialReceiverRequest.getMaterial()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(materialReceiverToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMaterialReceiverResponse updateMaterialReceiver(BigInteger id, PatchMaterialReceiverRequest patchMaterialReceiverRequest) {
        materialReceiverService.find(id)
                .ifPresentOrElse(
                        materialReceiver -> materialReceiverService.update(updateMaterialReceiverWithRequestFunction.apply(materialReceiver, patchMaterialReceiverRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
        return getMaterialReceiver(id);
    }

    @Override
    public void deleteMaterialReceiver(BigInteger id) {
        materialReceiverService.find(id)
                .ifPresentOrElse(
                        materialReceiver -> materialReceiverService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
