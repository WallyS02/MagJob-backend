package com.keepitup.magjobbackend.materialreceiver.service.impl;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.materialreceiver.repository.api.MaterialReceiverRepository;
import com.keepitup.magjobbackend.materialreceiver.service.api.MaterialReceiverService;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialReceiverDefaultService implements MaterialReceiverService {
    private final MaterialReceiverRepository materialReceiverRepository;

    @Autowired
    public MaterialReceiverDefaultService(MaterialReceiverRepository materialReceiverRepository) {
        this.materialReceiverRepository = materialReceiverRepository;
    }

    @Override
    public Optional<MaterialReceiver> find(BigInteger id) {
        return materialReceiverRepository.findById(id);
    }

    @Override
    public List<MaterialReceiver> findAll() {
        return materialReceiverRepository.findAll();
    }

    @Override
    public List<MaterialReceiver> findAllByMember(Member member) {
        return materialReceiverRepository.findAllByMember(member);
    }

    @Override
    public List<MaterialReceiver> findAllByMaterial(Material material) {
        return materialReceiverRepository.findAllByMaterial(material);
    }

    @Override
    public Optional<MaterialReceiver> findByMemberAndMaterial(Member member, Material material) {
        return materialReceiverRepository.findByMemberAndMaterial(member, material);
    }

    @Override
    public void create(MaterialReceiver materialReceiver) {
        materialReceiverRepository.save(materialReceiver);
    }

    @Override
    public void delete(BigInteger id) {
        materialReceiverRepository.findById(id).ifPresent(materialReceiverRepository::delete);
    }

    @Override
    public void update(MaterialReceiver materialReceiver) {
        materialReceiverRepository.save(materialReceiver);
    }
}
