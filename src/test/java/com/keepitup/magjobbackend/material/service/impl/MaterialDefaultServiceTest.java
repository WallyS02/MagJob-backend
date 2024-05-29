package com.keepitup.magjobbackend.material.service.impl;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.material.repository.api.MaterialRepository;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MaterialDefaultServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    private MaterialDefaultService materialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        materialService = new MaterialDefaultService(materialRepository);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        Material expectedMaterial = new Material();
        when(materialRepository.findById(id)).thenReturn(Optional.of(expectedMaterial));

        // Act
        Optional<Material> result = materialService.find(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedMaterial, result.get());
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Material> expectedMaterials = new ArrayList<>();
        expectedMaterials.add(new Material());
        expectedMaterials.add(new Material());
        when(materialRepository.findAll()).thenReturn(expectedMaterials);

        // Act
        List<Material> result = materialService.findAll();

        // Assert
        assertEquals(expectedMaterials.size(), result.size());
        assertEquals(expectedMaterials, result);
    }

    @Test
    void testFindByTitle() {
        // Arrange
        String title = "Test Material";
        Material expectedMaterial = new Material();
        when(materialRepository.findByTitle(title)).thenReturn(Optional.of(expectedMaterial));

        // Act
        Optional<Material> result = materialService.findByTitle(title);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedMaterial, result.get());
    }

    @Test
    void testFindAllByDescription() {
        // Arrange
        String description = "Test description";
        Page<Material> materials = new PageImpl<>(List.of(new Material()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(materialRepository.findAllByDescription(description, pageRequest)).thenReturn(materials);

        // Act
        Page<Material> result = materialService.findAllByDescription(description, pageRequest);

        // Assert
        assertEquals(materials.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(materials, result);
    }

    @Test
    void testFindAllBySize() {
        // Arrange
        Long size = 100L;
        Page<Material> materials = new PageImpl<>(List.of(new Material()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(materialRepository.findAllBySize(size, pageRequest)).thenReturn(materials);

        // Act
        Page<Material> result = materialService.findAllBySize(size, pageRequest);

        // Assert
        assertEquals(materials.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(materials, result);
    }

    @Test
    void testFindAllByContentType() {
        // Arrange
        String contentType = ".txt";
        Page<Material> materials = new PageImpl<>(List.of(new Material()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(materialRepository.findAllByContentType(contentType, pageRequest)).thenReturn(materials);

        // Act
        Page<Material> result = materialService.findAllByContentType(contentType, pageRequest);

        // Assert
        assertEquals(materials.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(materials, result);
    }

    @Test
    void testFindAllByDateOfCreation() {
        // Arrange
        ZonedDateTime dateOfCreation = ZonedDateTime.now();
        Page<Material> materials = new PageImpl<>(List.of(new Material()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(materialRepository.findAllByDateOfCreation(dateOfCreation, pageRequest)).thenReturn(materials);

        // Act
        Page<Material> result = materialService.findAllByDateOfCreation(dateOfCreation, pageRequest);

        // Assert
        assertEquals(materials.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(materials, result);
    }

    @Test
    void testFindAllByOrganization() {
        // Arrange
        Organization organization = new Organization();
        Page<Material> materials = new PageImpl<>(List.of(new Material()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(materialRepository.findAllByOrganization(organization, pageRequest)).thenReturn(materials);

        // Act
        Page<Material> result = materialService.findAllByOrganization(organization, pageRequest);

        // Assert
        assertEquals(materials.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(materials, result);
    }

    @Test
    void testCreate() {
        // Arrange
        Material material = new Material();

        // Act
        materialService.create(material);

        // Assert
        verify(materialRepository, times(1)).save(material);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        Material material = new Material();
        when(materialRepository.findById(id)).thenReturn(Optional.of(material));

        // Act
        materialService.delete(id);

        // Assert
        verify(materialRepository, times(1)).delete(material);
    }

    @Test
    void testUpdate() {
        // Arrange
        Material material = new Material();

        // Act
        materialService.update(material);

        // Assert
        verify(materialRepository, times(1)).save(material);
    }
}
