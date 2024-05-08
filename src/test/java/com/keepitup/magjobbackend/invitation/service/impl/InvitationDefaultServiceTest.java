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

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        BigInteger userId = BigInteger.ONE;
        User user = new User();
        List<Invitation> expectedInvitations = new ArrayList<>();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(invitationRepository.findAllByUser(user)).thenReturn(expectedInvitations);

        // Act
        Optional<List<Invitation>> result = invitationService.findAllByUser(userId);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(userRepository).findById(userId);
        verify(invitationRepository).findAllByUser(user);
    }

    @Test
    void testFindAllByUserAndIsActive() {
        // Arrange
        BigInteger userId = BigInteger.ONE;
        boolean isActive = true;
        User user = new User();
        List<Invitation> expectedInvitations = new ArrayList<>();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(invitationRepository.findAllByUserAndIsActive(user, isActive)).thenReturn(expectedInvitations);

        // Act
        Optional<List<Invitation>> result = invitationService.findAllByUserAndIsActive(userId, isActive);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(userRepository).findById(userId);
        verify(invitationRepository).findAllByUserAndIsActive(user, isActive);
    }

    @Test
    void testFindAllByOrganization() {
        // Arrange
        BigInteger organizationId = BigInteger.ONE;
        Organization organization = new Organization();
        List<Invitation> expectedInvitations = new ArrayList<>();
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        when(invitationRepository.findAllByOrganization(organization)).thenReturn(expectedInvitations);

        // Act
        Optional<List<Invitation>> result = invitationService.findAllByOrganization(organizationId);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(organizationRepository).findById(organizationId);
        verify(invitationRepository).findAllByOrganization(organization);
    }

    @Test
    void testFindAllByOrganizationAndIsActive() {
        // Arrange
        BigInteger organizationId = BigInteger.ONE;
        boolean isActive = true;
        Organization organization = new Organization();
        List<Invitation> expectedInvitations = new ArrayList<>();
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        when(invitationRepository.findAllByOrganizationAndIsActive(organization, isActive)).thenReturn(expectedInvitations);

        // Act
        Optional<List<Invitation>> result = invitationService.findAllByOrganizationAndIsActive(organizationId, isActive);

        // Assert
        assertEquals(expectedInvitations, result.orElse(null));
        verify(organizationRepository).findById(organizationId);
        verify(invitationRepository).findAllByOrganizationAndIsActive(organization, isActive);
    }

    @Test
    void testFindByUserAndOrganization() {
        // Arrange
        BigInteger userId = BigInteger.ONE;
        BigInteger organizationId = BigInteger.ONE;
        Invitation expectedInvitation = new Invitation();
        when(invitationRepository.findByUser_IdAndOrganization_Id(userId, organizationId)).thenReturn(Optional.of(expectedInvitation));

        // Act
        Optional<Invitation> result = invitationService.findByUserAndOrganization(userId, organizationId);

        // Assert
        assertEquals(expectedInvitation, result.orElse(null));
        verify(invitationRepository).findByUser_IdAndOrganization_Id(userId, organizationId);
    }

    @Test
    void testFindAllByDateOfCreation() {
        // Arrange
        ZonedDateTime dateOfCreation = ZonedDateTime.now();
        List<Invitation> expectedInvitations = new ArrayList<>();
        when(invitationRepository.findAllByDateOfCreation(dateOfCreation)).thenReturn(expectedInvitations);

        // Act
        List<Invitation> result = invitationService.findAllByDateOfCreation(dateOfCreation);

        // Assert
        assertEquals(expectedInvitations, result);
        verify(invitationRepository).findAllByDateOfCreation(dateOfCreation);
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
        BigInteger userId = BigInteger.ONE;
        BigInteger organizationId = BigInteger.ONE;
        Invitation invitation = new Invitation();
        when(invitationRepository.findByUser_IdAndOrganization_Id(userId, organizationId)).thenReturn(Optional.of(invitation));

        // Act
        invitationService.delete(userId, organizationId);

        // Assert
        verify(invitationRepository).findByUser_IdAndOrganization_Id(userId, organizationId);
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
