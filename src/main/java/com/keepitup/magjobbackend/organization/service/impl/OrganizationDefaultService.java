package com.keepitup.magjobbackend.organization.service.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import com.keepitup.magjobbackend.organization.service.api.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationDefaultService implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationDefaultService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<Organization> find(BigInteger id) {
        return organizationRepository.findById(id);
    }

    @Override
    public Optional<Organization> findByName(String name) {
        return organizationRepository.findByName(name);
    }

    @Override
    public List<Organization> findAllByDateOfCreation(ZonedDateTime dateOfCreation) {
        return organizationRepository.findAllByDateOfCreation(dateOfCreation);
    }

    @Override
    public void create(Organization organization) {
        organization.setDateOfCreation(ZonedDateTime.now());
        organizationRepository.save(organization);
    }

    @Override
    public void delete(BigInteger id) {
        organizationRepository.findById(id).ifPresent(organizationRepository::delete);
    }

    @Override
    public void update(Organization organization) {
        organizationRepository.save(organization);
    }
}
