package com.keepitup.magjobbackend.materialreceiver.service.impl;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.materialreceiver.repository.api.MaterialReceiverRepository;
import com.keepitup.magjobbackend.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaterialReceiverDefaultServiceTest {

    @Mock
    private MaterialReceiverRepository materialReceiverRepository;

    private MaterialReceiverDefaultService materialReceiverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        materialReceiverService = new MaterialReceiverDefaultService(materialReceiverRepository);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        MaterialReceiver expectedMaterialReceiver = new MaterialReceiver();
        when(materialReceiverRepository.findById(id)).thenReturn(Optional.of(expectedMaterialReceiver));

        // Act
        Optional<MaterialReceiver> result = materialReceiverService.find(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedMaterialReceiver, result.get());
        verify(materialReceiverRepository, times(1)).findById(id);
    }

    @Test
    void testFindAll() {
        // Arrange
        MaterialReceiver materialReceiver1 = new MaterialReceiver();
        MaterialReceiver materialReceiver2 = new MaterialReceiver();
        when(materialReceiverRepository.findAll()).thenReturn(Arrays.asList(materialReceiver1, materialReceiver2));

        // Act
        List<MaterialReceiver> result = materialReceiverService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(materialReceiver1));
        assertTrue(result.contains(materialReceiver2));
        verify(materialReceiverRepository, times(1)).findAll();
    }

    @Test
    void testFindAllByMember() {
        // Arrange
        Member member = new Member();
        MaterialReceiver materialReceiver1 = new MaterialReceiver();
        MaterialReceiver materialReceiver2 = new MaterialReceiver();
        when(materialReceiverRepository.findAllByMember(member)).thenReturn(Arrays.asList(materialReceiver1, materialReceiver2));

        // Act
        List<MaterialReceiver> result = materialReceiverService.findAllByMember(member);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(materialReceiver1));
        assertTrue(result.contains(materialReceiver2));
        verify(materialReceiverRepository, times(1)).findAllByMember(member);
    }

    @Test
    void testFindAllByMaterial() {
        // Arrange
        Material material = new Material();
        MaterialReceiver materialReceiver1 = new MaterialReceiver();
        MaterialReceiver materialReceiver2 = new MaterialReceiver();
        when(materialReceiverRepository.findAllByMaterial(material)).thenReturn(Arrays.asList(materialReceiver1, materialReceiver2));

        // Act
        List<MaterialReceiver> result = materialReceiverService.findAllByMaterial(material);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(materialReceiver1));
        assertTrue(result.contains(materialReceiver2));
        verify(materialReceiverRepository, times(1)).findAllByMaterial(material);
    }

    @Test
    void testFindByMemberAndMaterial() {
        // Arrange
        Member member = new Member();
        Material material = new Material();
        MaterialReceiver expectedMaterialReceiver = new MaterialReceiver();
        when(materialReceiverRepository.findByMemberAndMaterial(member, material)).thenReturn(Optional.of(expectedMaterialReceiver));

        // Act
        Optional<MaterialReceiver> result = materialReceiverService.findByMemberAndMaterial(member, material);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedMaterialReceiver, result.get());
        verify(materialReceiverRepository, times(1)).findByMemberAndMaterial(member, material);
    }

    @Test
    void testCreate() {
        // Arrange
        MaterialReceiver materialReceiver = new MaterialReceiver();

        // Act
        materialReceiverService.create(materialReceiver);

        // Assert
        verify(materialReceiverRepository, times(1)).save(materialReceiver);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        MaterialReceiver materialReceiver = new MaterialReceiver();
        when(materialReceiverRepository.findById(id)).thenReturn(Optional.of(materialReceiver));

        // Act
        materialReceiverService.delete(id);

        // Assert
        verify(materialReceiverRepository, times(1)).findById(id);
        verify(materialReceiverRepository, times(1)).delete(materialReceiver);
    }

    @Test
    void testUpdate() {
        // Arrange
        MaterialReceiver materialReceiver = new MaterialReceiver();

        // Act
        materialReceiverService.update(materialReceiver);

        // Assert
        verify(materialReceiverRepository, times(1)).save(materialReceiver);
    }
}
