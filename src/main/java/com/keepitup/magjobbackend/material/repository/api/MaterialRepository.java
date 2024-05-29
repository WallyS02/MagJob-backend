package com.keepitup.magjobbackend.material.repository.api;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, BigInteger> {
    Optional<Material> findByTitle(String title);

    Page<Material> findAllByDescription(String description, Pageable pageable);

    Page<Material> findAllBySize(Long size, Pageable pageable);

    Page<Material> findAllByContentType(String contentType, Pageable pageable);

    Page<Material> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable);

    Page<Material> findAllByOrganization(Organization organization, Pageable pageable);
}
