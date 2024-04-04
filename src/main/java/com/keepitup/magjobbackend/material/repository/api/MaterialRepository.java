package com.keepitup.magjobbackend.material.repository.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, BigInteger> {
    Optional<Material> findByTitle(String title);

    List<Material> findAllByDescription(String description);

    List<Material> findAllBySize(Long size);

    List<Material> findAllByContentType(String contentType);

    List<Material> findAllByDateOfCreation(ZonedDateTime dateOfCreation);

    List<Material> findAllByOrganization(Organization organization);
}
