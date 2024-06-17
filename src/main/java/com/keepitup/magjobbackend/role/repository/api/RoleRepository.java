package com.keepitup.magjobbackend.role.repository.api;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, BigInteger> {
    Optional<Role> findByName(String name);

    Optional<Role> findByExternalId(String externalId);
    List<Role> findAllByOrganization(Organization organization);

    Optional<Role> findByNameAndOrganization(String name, Organization organization);
}
