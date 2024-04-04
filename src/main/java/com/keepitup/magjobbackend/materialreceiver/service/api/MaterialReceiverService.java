package com.keepitup.magjobbackend.materialreceiver.service.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.member.entity.Member;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MaterialReceiverService {
    Optional<MaterialReceiver> find(BigInteger id);

    List<MaterialReceiver> findAll();

    List<MaterialReceiver> findAllByMember(Member member);

    List<MaterialReceiver> findAllByMaterial(Material material);

    Optional<MaterialReceiver> findByMemberAndMaterial(Member member, Material material);

    void create(MaterialReceiver materialReceiver);

    void delete(BigInteger id);

    void update(MaterialReceiver materialReceiver);
}
