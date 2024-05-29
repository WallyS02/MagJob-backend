package com.keepitup.magjobbackend.material.service.impl;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.material.repository.api.MaterialRepository;
import com.keepitup.magjobbackend.material.service.api.MaterialService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Material> findAll(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    @Override
    public Optional<Material> findByTitle(String title) {
        return materialRepository.findByTitle(title);
    }

    @Override
    public Page<Material> findAllByDescription(String description, Pageable pageable) {
        return materialRepository.findAllByDescription(description, pageable);
    }

    @Override
    public Page<Material> findAllBySize(Long size, Pageable pageable) {
        return materialRepository.findAllBySize(size, pageable);
    }

    @Override
    public Page<Material> findAllByContentType(String contentType, Pageable pageable) {
        return materialRepository.findAllByContentType(contentType, pageable);
    }

    @Override
    public Page<Material> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable) {
        return materialRepository.findAllByDateOfCreation(dateOfCreation, pageable);
    }

    @Override
    public Page<Material> findAllByOrganization(Organization organization, Pageable pageable) {
        return materialRepository.findAllByOrganization(organization, pageable);
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
