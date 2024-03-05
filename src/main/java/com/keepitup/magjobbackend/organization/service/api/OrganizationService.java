package com.keepitup.magjobbackend.organization.service.api;

import com.keepitup.magjobbackend.organization.entity.Organization;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    List<Organization> findAll();

    Optional<Organization> find(BigInteger id);

    Optional<Organization> findByName(String name);

    List<Organization> findAllByDateOfCreation(ZonedDateTime dateOfCreation);

    void create(Organization organization);

    void delete(BigInteger id);

    void update(Organization organization);
}
