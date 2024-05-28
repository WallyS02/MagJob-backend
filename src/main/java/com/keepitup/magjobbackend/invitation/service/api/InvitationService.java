package com.keepitup.magjobbackend.invitation.service.api;

import com.keepitup.magjobbackend.invitation.entity.Invitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;

import java.util.List;

public interface InvitationService {

    Optional<Page<Invitation>> findAllByUser(BigInteger userId, Pageable pageable);

    Optional<Page<Invitation>> findAllByUserAndIsActive(BigInteger userId, Boolean isActive, Pageable pageable);

    Optional<Page<Invitation>> findAllByOrganization(BigInteger organizationId, Pageable pageable);

    Optional<Page<Invitation>> findAllByOrganizationAndIsActive(BigInteger organizationId, Boolean isActive, Pageable pageable);

    Optional<Invitation> findByUserAndOrganization(BigInteger userId, BigInteger organizationId);

    Page<Invitation> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable);

    void create(Invitation invitation);

    void delete(BigInteger userId, BigInteger organizationId);

    void update(Invitation invitation);
}
