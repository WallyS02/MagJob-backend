package com.keepitup.magjobbackend.material.service.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface MaterialService {
    Optional<Material> find(BigInteger id);

    List<Material> findAll();

    Page<Material> findAll(Pageable pageable);

    Optional<Material> findByTitle(String title);

    Page<Material> findAllByDescription(String description, Pageable pageable);

    Page<Material> findAllBySize(Long size, Pageable pageable);

    Page<Material> findAllByContentType(String contentType, Pageable pageable);

    Page<Material> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable);

    Page<Material> findAllByOrganization(Organization organization, Pageable pageable);

    void create(Material material);

    void delete(BigInteger id);

    void update(Material material);
}
