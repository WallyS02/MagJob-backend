package com.keepitup.magjobbackend.invitation.repository.api;

import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, BigInteger> {
    List<Invitation> findAllByUser(User user);
    List<Invitation> findAllByUserAndIsActive(User user, Boolean isActive);
    List<Invitation> findAllByOrganization(Organization organization);
    List<Invitation> findAllByOrganizationAndIsActive(Organization organization, Boolean isActive);
    List<Invitation> findAllByDateOfCreation(ZonedDateTime dateOfCreation);
    Optional<Invitation> findByUserIdAndOrganizationId(UUID userId, BigInteger organizationId);
}
