package com.keepitup.magjobbackend.role.service.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.role.repository.api.RoleRepository;
import com.keepitup.magjobbackend.role.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class RoleDefaultService implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleDefaultService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> find(BigInteger id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional<Role> findByExternalId(String externalId) {
        return roleRepository.findByExternalId(externalId);
    }

    @Override
    public Optional<Role> findByNameAndOrganization(String name, Organization organization) {
        return roleRepository.findByNameAndOrganization(name, organization);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Page<Role> findAllByOrganization(Organization organization, Pageable pageable) {
        return roleRepository.findAllByOrganization(organization, pageable);
    }

    @Override
    public void create(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(BigInteger id) {
        roleRepository.findById(id).ifPresent(roleRepository::delete);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }
}
