package com.keepitup.magjobbackend.role.service.api;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.role.entity.Role;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> find(BigInteger id);

    Optional<Role> findByName(String name);

    Optional<Role> findByExternalId(String externalId);

    List<Role> findAll();

    List<Role> findAllByOrganization(Organization organization);

    void create(Role role);

    void delete(BigInteger id);

    void update(Role role);
}
