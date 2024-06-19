package com.keepitup.magjobbackend.invitation.service.impl;

import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.invitation.repository.api.InvitationRepository;
import com.keepitup.magjobbackend.invitation.service.api.InvitationService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvitationDefaultServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    private InvitationService invitationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invitationService = new InvitationDefaultService(invitationRepository, userRepository, organizationRepository);
    }

    @Test
    void testFindAllByUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        Page<Invitation> expectedInvitations = new PageImpl<>(List.of(new Invitation()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(invitationRepository.findAllByUser(user, pageRequest)).thenReturn(expectedInvitations);

        // Act
        Optional<Page<Invitation>> result = invitationService.findAllByUser(userId, pageRequest);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(userRepository).findById(userId);
        verify(invitationRepository).findAllByUser(user, pageRequest);
    }

    @Test
    void testFindAllByUserAndIsActive() {
        // Arrange
        UUID userId = UUID.randomUUID();
        boolean isActive = true;
        User user = new User();
        Page<Invitation> expectedInvitations = new PageImpl<>(List.of(new Invitation()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(invitationRepository.findAllByUserAndIsActive(user, isActive, pageRequest)).thenReturn(expectedInvitations);

        // Act
        Optional<Page<Invitation>> result = invitationService.findAllByUserAndIsActive(userId, isActive, pageRequest);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(userRepository).findById(userId);
        verify(invitationRepository).findAllByUserAndIsActive(user, isActive, pageRequest);
    }

    @Test
    void testFindAllByOrganization() {
        // Arrange
        BigInteger organizationId = BigInteger.ONE;
        Organization organization = new Organization();
        Page<Invitation> expectedInvitations = new PageImpl<>(List.of(new Invitation()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        when(invitationRepository.findAllByOrganization(organization, pageRequest)).thenReturn(expectedInvitations);

        // Act
        Optional<Page<Invitation>> result = invitationService.findAllByOrganization(organizationId, pageRequest);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(organizationRepository).findById(organizationId);
        verify(invitationRepository).findAllByOrganization(organization, pageRequest);
    }

    @Test
    void testFindAllByOrganizationAndIsActive() {
        // Arrange
        BigInteger organizationId = BigInteger.ONE;
        boolean isActive = true;
        Organization organization = new Organization();
        Page<Invitation> expectedInvitations = new PageImpl<>(List.of(new Invitation()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        when(invitationRepository.findAllByOrganizationAndIsActive(organization, isActive, pageRequest)).thenReturn(expectedInvitations);

        // Act
        Optional<Page<Invitation>> result = invitationService.findAllByOrganizationAndIsActive(organizationId, isActive, pageRequest);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(organizationRepository).findById(organizationId);
        verify(invitationRepository).findAllByOrganizationAndIsActive(organization, isActive, pageRequest);
    }

    @Test
    void testFindByUserAndOrganization() {
        // Arrange
        UUID userId = UUID.randomUUID();
        BigInteger organizationId = BigInteger.ONE;
        Invitation expectedInvitation = new Invitation();
        when(invitationRepository.findByUserIdAndOrganizationId(userId, organizationId)).thenReturn(Optional.of(expectedInvitation));

        // Act
        Optional<Invitation> result = invitationService.findByUserAndOrganization(userId, organizationId);

        // Assert
        assertEquals(expectedInvitation, result.orElse(null));
        verify(invitationRepository).findByUserIdAndOrganizationId(userId, organizationId);
    }

    @Test
    void testFindAllByDateOfCreation() {
        // Arrange
        ZonedDateTime dateOfCreation = ZonedDateTime.now();
        Page<Invitation> expectedInvitations = new PageImpl<>(List.of(new Invitation()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(invitationRepository.findAllByDateOfCreation(dateOfCreation, pageRequest)).thenReturn(expectedInvitations);

        // Act
        Page<Invitation> result = invitationService.findAllByDateOfCreation(dateOfCreation, pageRequest);

        // Assert
        assertEquals(expectedInvitations, result);
        verify(invitationRepository).findAllByDateOfCreation(dateOfCreation, pageRequest);
    }

    @Test
    void testCreate() {
        // Arrange
        Invitation invitation = new Invitation();

        // Act
        invitationService.create(invitation);

        // Assert
        verify(invitationRepository).save(invitation);
        assertNotNull(invitation.getDateOfCreation());
        assertTrue(invitation.getIsActive());
    }

    @Test
    void testDelete() {
        // Arrange
        UUID userId = UUID.randomUUID();
        BigInteger organizationId = BigInteger.ONE;
        Invitation invitation = new Invitation();
        when(invitationRepository.findByUserIdAndOrganizationId(userId, organizationId)).thenReturn(Optional.of(invitation));

        // Act
        invitationService.delete(userId, organizationId);

        // Assert
        verify(invitationRepository).findByUserIdAndOrganizationId(userId, organizationId);
        verify(invitationRepository).delete(invitation);
    }

    @Test
    void testUpdate() {
        // Arrange
        Invitation invitation = new Invitation();
        when(invitationRepository.save(invitation)).thenReturn(invitation);

        // Act
        invitationService.update(invitation);

        // Assert
        verify(invitationRepository).save(invitation);
    }
}
