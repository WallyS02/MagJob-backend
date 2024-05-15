package com.keepitup.magjobbackend.invitation.service.impl;

import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.invitation.repository.api.InvitationRepository;
import com.keepitup.magjobbackend.invitation.service.api.InvitationService;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationDefaultService implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public InvitationDefaultService(InvitationRepository invitationRepository,
                                    UserRepository userRepository,
                                    OrganizationRepository organizationRepository
    ) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }


    @Override
    public Optional<List<Invitation>> findAllByUser(UUID userId) {
        return userRepository.findById(userId)
                .map(invitationRepository::findAllByUser);
    }

    @Override
    public Optional<List<Invitation>> findAllByUserAndIsActive(UUID userId, Boolean isActive) {
        return userRepository.findById(userId)
                .map(user -> invitationRepository.findAllByUserAndIsActive(user, isActive));
    }

    @Override
    public Optional<List<Invitation>> findAllByOrganization(BigInteger organizationId) {
        return organizationRepository.findById(organizationId)
                .map(invitationRepository::findAllByOrganization);
    }

    @Override
    public Optional<List<Invitation>> findAllByOrganizationAndIsActive(BigInteger organizationId, Boolean isActive) {
        return organizationRepository.findById(organizationId)
                .map(organization -> invitationRepository.findAllByOrganizationAndIsActive(organization, isActive));
    }

    @Override
    public Optional<Invitation> findByUserAndOrganization(UUID userId, BigInteger organizationId) {
        return invitationRepository.findByUserIdAndOrganizationId(userId, organizationId);
    }

    @Override
    public List<Invitation> findAllByDateOfCreation(ZonedDateTime dateOfCreation) {
        return invitationRepository.findAllByDateOfCreation(dateOfCreation);
    }

    @Override
    public void create(Invitation invitation) {
        invitation.setDateOfCreation(ZonedDateTime.now());
        invitation.setIsActive(true);
        invitationRepository.save(invitation);
    }

    @Override
    public void delete(UUID userId, BigInteger organizationId) {
        invitationRepository.findByUserIdAndOrganizationId(userId, organizationId).ifPresent(invitationRepository::delete);
    }

    @Override
    public void update(Invitation invitation) {
        invitationRepository.save(invitation);
    }
}
