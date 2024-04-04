package com.keepitup.magjobbackend.materialreceiver.repository.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MaterialReceiverRepository extends JpaRepository<MaterialReceiver, BigInteger> {
    List<MaterialReceiver> findAllByMember(Member member);

    List<MaterialReceiver> findAllByMaterial(Material material);

    Optional<MaterialReceiver> findByMemberAndMaterial(Member member, Material material);
}
