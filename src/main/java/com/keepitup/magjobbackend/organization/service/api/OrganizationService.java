package com.keepitup.magjobbackend.organization.service.api;

import com.keepitup.magjobbackend.organization.entity.Organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    List<Organization> findAll();

    Page<Organization> findAll(Pageable pageable);

    Optional<Organization> find(BigInteger id);

    Optional<Organization> findByName(String name);

    Page<Organization> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable);

    void create(Organization organization);

    void delete(BigInteger id);

    void update(Organization organization);
}
