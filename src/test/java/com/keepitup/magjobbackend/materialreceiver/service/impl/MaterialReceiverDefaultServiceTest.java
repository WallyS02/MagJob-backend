package com.keepitup.magjobbackend.materialreceiver.service.impl;

import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.materialreceiver.entity.MaterialReceiver;
import com.keepitup.magjobbackend.materialreceiver.repository.api.MaterialReceiverRepository;
import com.keepitup.magjobbackend.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
        Page<MaterialReceiver> materialReceivers = new PageImpl<>(List.of(new MaterialReceiver()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(materialReceiverRepository.findAllByMember(member, pageRequest)).thenReturn(materialReceivers);

        // Act
        Page<MaterialReceiver> result = materialReceiverService.findAllByMember(member, pageRequest);

        // Assert
        assertEquals(materialReceivers.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(materialReceivers, result);
        verify(materialReceiverRepository, times(1)).findAllByMember(member, pageRequest);
    }

    @Test
    void testFindAllByMaterial() {
        // Arrange
        Material material = new Material();
        Page<MaterialReceiver> materialReceivers = new PageImpl<>(List.of(new MaterialReceiver()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(materialReceiverRepository.findAllByMaterial(material, pageRequest)).thenReturn(materialReceivers);

        // Act
        Page<MaterialReceiver> result = materialReceiverService.findAllByMaterial(material, pageRequest);

        // Assert
        assertEquals(materialReceivers.getNumberOfElements(), result.getNumberOfElements());
        assertEquals(materialReceivers, result);
        verify(materialReceiverRepository, times(1)).findAllByMaterial(material, pageRequest);
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
