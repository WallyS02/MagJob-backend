package com.keepitup.magjobbackend.material.service.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.organization.entity.Organization;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface MaterialService {
    Optional<Material> find(BigInteger id);

    List<Material> findAll();

    Optional<Material> findByTitle(String title);

    List<Material> findAllByDescription(String description);

    List<Material> findAllBySize(Long size);

    List<Material> findAllByContentType(String contentType);

    List<Material> findAllByDateOfCreation(ZonedDateTime dateOfCreation);

    List<Material> findAllByOrganization(Organization organization);

    void create(Material material);

    void delete(BigInteger id);

    void update(Material material);
}
