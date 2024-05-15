package com.keepitup.magjobbackend.invitation.service.api;

import com.keepitup.magjobbackend.invitation.entity.Invitation;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;

import java.util.List;
import java.util.UUID;

public interface InvitationService {

    Optional<List<Invitation>> findAllByUser(UUID userId);

    Optional<List<Invitation>> findAllByUserAndIsActive(UUID userId, Boolean isActive);

    Optional<List<Invitation>> findAllByOrganization(BigInteger organizationId);

    Optional<List<Invitation>> findAllByOrganizationAndIsActive(BigInteger organizationId, Boolean isActive);

    Optional<Invitation> findByUserAndOrganization(UUID userId, BigInteger organizationId);

    List<Invitation> findAllByDateOfCreation(ZonedDateTime dateOfCreation);

    void create(Invitation invitation);

    void delete(UUID userId, BigInteger organizationId);

    void update(Invitation invitation);
}
