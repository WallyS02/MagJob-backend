package com.keepitup.magjobbackend.organization.service.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationDefaultServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationDefaultService organizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ReturnsListOfOrganizations() {
        // Arrange
        List<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization());
        when(organizationRepository.findAll()).thenReturn(organizations);

        // Act
        List<Organization> result = organizationService.findAll();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void find_ReturnsOrganizationById() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        Organization organization = new Organization();
        when(organizationRepository.findById(id)).thenReturn(Optional.of(organization));

        // Act
        Optional<Organization> result = organizationService.find(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(organization, result.get());
    }

    @Test
    void findByName_ReturnsOrganizationByName() {
        // Arrange
        String name = "Test Organization";
        Organization organization = new Organization();
        when(organizationRepository.findByName(name)).thenReturn(Optional.of(organization));

        // Act
        Optional<Organization> result = organizationService.findByName(name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(organization, result.get());
    }

    @Test
    void findAllByDateOfCreation_ReturnsListOfOrganizations() {
        // Arrange
        ZonedDateTime date = ZonedDateTime.now();
        Page<Organization> organizations = new PageImpl<>(List.of(new Organization()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(organizationRepository.findAllByDateOfCreation(date, pageRequest)).thenReturn(organizations);

        // Act
        Page<Organization> result = organizationService.findAllByDateOfCreation(date, pageRequest);

        // Assert
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void create_SavesOrganizationWithCurrentDate() {
        // Arrange
        Organization organization = new Organization();

        // Act
        organizationService.create(organization);

        // Assert
        assertNotNull(organization.getDateOfCreation());
    }

    @Test
    void delete_DeletesOrganizationById() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        Organization organization = new Organization();
        when(organizationRepository.findById(id)).thenReturn(Optional.of(organization));

        // Act
        organizationService.delete(id);

        // Assert
        verify(organizationRepository, times(1)).delete(organization);
    }

    @Test
    void update_SavesOrganization() {
        // Arrange
        Organization organization = new Organization();

        // Act
        organizationService.update(organization);

        // Assert
        verify(organizationRepository, times(1)).save(organization);
    }
}
