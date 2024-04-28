package com.keepitup.magjobbackend.material.service.impl;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.material.repository.api.MaterialRepository;
import com.keepitup.magjobbackend.material.service.api.MaterialService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialDefaultService implements MaterialService {
    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialDefaultService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Optional<Material> find(BigInteger id) {
        return materialRepository.findById(id);
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public Optional<Material> findByTitle(String title) {
        return materialRepository.findByTitle(title);
    }

    @Override
    public List<Material> findAllByDescription(String description) {
        return materialRepository.findAllByDescription(description);
    }

    @Override
    public List<Material> findAllBySize(Long size) {
        return materialRepository.findAllBySize(size);
    }

    @Override
    public List<Material> findAllByContentType(String contentType) {
        return materialRepository.findAllByContentType(contentType);
    }

    @Override
    public List<Material> findAllByDateOfCreation(ZonedDateTime dateOfCreation) {
        return materialRepository.findAllByDateOfCreation(dateOfCreation);
    }

    @Override
    public List<Material> findAllByOrganization(Organization organization) {
        return materialRepository.findAllByOrganization(organization);
    }

    @Override
    public void create(Material material) {
        materialRepository.save(material);
    }

    @Override
    public void delete(BigInteger id) {
        materialRepository.findById(id).ifPresent(materialRepository::delete);
    }

    @Override
    public void update(Material material) {
        materialRepository.save(material);
    }
}
