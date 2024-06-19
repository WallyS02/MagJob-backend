package com.keepitup.magjobbackend.invitation.repository.api;

import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, BigInteger> {
    Page<Invitation> findAllByUser(User user, Pageable pageable);
    Page<Invitation> findAllByUserAndIsActive(User user, Boolean isActive, Pageable pageable);
    Page<Invitation> findAllByOrganization(Organization organization, Pageable pageable);
    Page<Invitation> findAllByOrganizationAndIsActive(Organization organization, Boolean isActive, Pageable pageable);
    Page<Invitation> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable);
    Optional<Invitation> findByUserIdAndOrganizationId(UUID userId, BigInteger organizationId);
}
