package com.keepitup.magjobbackend.invitation.service.impl;

import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.invitation.repository.api.InvitationRepository;
import com.keepitup.magjobbackend.invitation.service.api.InvitationService;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.ZonedDateTime;
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
    public Optional<Page<Invitation>> findAllByUser(UUID userId, Pageable pageable) {
        return userRepository.findById(userId)
                .map(invitation -> invitationRepository.findAllByUser(invitation, pageable));
    }

    @Override
    public Optional<Page<Invitation>> findAllByUserAndIsActive(UUID userId, Boolean isActive, Pageable pageable) {
        return userRepository.findById(userId)
                .map(user -> invitationRepository.findAllByUserAndIsActive(user, isActive, pageable));
    }

    @Override
    public Optional<Page<Invitation>> findAllByOrganization(BigInteger organizationId, Pageable pageable) {
        return organizationRepository.findById(organizationId)
                .map(invitation -> invitationRepository.findAllByOrganization(invitation, pageable));
    }

    @Override
    public Optional<Page<Invitation>> findAllByOrganizationAndIsActive(BigInteger organizationId, Boolean isActive, Pageable pageable) {
        return organizationRepository.findById(organizationId)
                .map(organization -> invitationRepository.findAllByOrganizationAndIsActive(organization, isActive, pageable));
    }

    @Override
    public Optional<Invitation> findByUserAndOrganization(UUID userId, BigInteger organizationId) {
        return invitationRepository.findByUserIdAndOrganizationId(userId, organizationId);
    }

    @Override
    public Page<Invitation> findAllByDateOfCreation(ZonedDateTime dateOfCreation, Pageable pageable) {
        return invitationRepository.findAllByDateOfCreation(dateOfCreation, pageable);
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
