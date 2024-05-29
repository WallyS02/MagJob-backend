package com.keepitup.magjobbackend.materialreceiver.repository.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface MaterialReceiverRepository extends JpaRepository<MaterialReceiver, BigInteger> {
    Page<MaterialReceiver> findAllByMember(Member member, Pageable pageable);

    Page<MaterialReceiver> findAllByMaterial(Material material, Pageable pageable);

    Optional<MaterialReceiver> findByMemberAndMaterial(Member member, Material material);
}
