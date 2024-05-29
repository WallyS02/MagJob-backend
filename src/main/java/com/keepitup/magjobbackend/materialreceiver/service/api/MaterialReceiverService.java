package com.keepitup.magjobbackend.materialreceiver.service.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MaterialReceiverService {
    Optional<MaterialReceiver> find(BigInteger id);

    List<MaterialReceiver> findAll();

    Page<MaterialReceiver> findAll(Pageable pageable);

    Page<MaterialReceiver> findAllByMember(Member member, Pageable pageable);

    Page<MaterialReceiver> findAllByMaterial(Material material, Pageable pageable);

    Optional<MaterialReceiver> findByMemberAndMaterial(Member member, Material material);

    void create(MaterialReceiver materialReceiver);

    void delete(BigInteger id);

    void update(MaterialReceiver materialReceiver);
}
