package com.keepitup.magjobbackend.role.service.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.role.repository.api.RoleRepository;
import com.keepitup.magjobbackend.role.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findAllByOrganization(Organization organization) {
        return roleRepository.findAllByOrganization(organization);
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
