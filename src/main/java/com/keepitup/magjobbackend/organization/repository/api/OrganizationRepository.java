package com.keepitup.magjobbackend.organization.repository.api;

import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, BigInteger> {
    Optional<Organization> findByName(String name);
    List<Organization> findAllByDateOfCreation(ZonedDateTime dateOfCreation);
}
