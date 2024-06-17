package com.keepitup.magjobbackend.role.service.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.role.repository.api.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleDefaultServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleDefaultService roleDefaultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        Role role = new Role();
        role.setId(id);
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleDefaultService.find(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void testFindByName() {
        // Arrange
        String name = "admin";
        Role role = new Role();
        role.setName(name);
        when(roleRepository.findByName(name)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleDefaultService.findByName(name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
    }

    @Test
    void testFindByExternalId() {
        // Arrange
        String externalId = UUID.randomUUID().toString();
        Role role = new Role();
        role.setExternalId(externalId);
        when(roleRepository.findByExternalId(externalId)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> result = roleDefaultService.findByExternalId(externalId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(externalId, result.get().getExternalId());
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Role> roles = Arrays.asList(new Role(), new Role());
        when(roleRepository.findAll()).thenReturn(roles);

        // Act
        List<Role> result = roleDefaultService.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllWithPagination() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Role> page = new PageImpl<>(Arrays.asList(new Role(), new Role()));
        when(roleRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<Role> result = roleDefaultService.findAll(pageable);

        // Assert
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testFindAllByOrganization() {
        // Arrange
        Organization organization = new Organization();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Role> page = new PageImpl<>(Arrays.asList(new Role(), new Role()));
        when(roleRepository.findAllByOrganization(organization, pageable)).thenReturn(page);

        // Act
        Page<Role> result = roleDefaultService.findAllByOrganization(organization, pageable);

        // Assert
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testCreate() {
        // Arrange
        Role role = new Role();

        // Act
        roleDefaultService.create(role);

        // Assert
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger id = BigInteger.valueOf(1);
        Role role = new Role();
        role.setId(id);
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        // Act
        roleDefaultService.delete(id);

        // Assert
        verify(roleRepository, times(1)).delete(role);
    }

    @Test
    void testUpdate() {
        // Arrange
        Role role = new Role();

        // Act
        roleDefaultService.update(role);

        // Assert
        verify(roleRepository, times(1)).save(role);
    }
}
