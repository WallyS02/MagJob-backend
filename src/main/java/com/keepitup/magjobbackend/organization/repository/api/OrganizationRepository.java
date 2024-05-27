package com.keepitup.magjobbackend.organization.repository.api;

import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, BigInteger> {
    Optional<Organization> findByName(String name);

    Page<Organization> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable);
}
