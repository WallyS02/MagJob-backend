package com.keepitup.magjobbackend.rolemember.service.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import com.keepitup.magjobbackend.rolemember.repository.api.RoleMemberRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleMemberDefaultServiceTest {

    @Mock
    private RoleMemberRepository roleMemberRepository;

    @InjectMocks
    private RoleMemberDefaultService roleMemberDefaultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        RoleMember roleMember = new RoleMember();
        when(roleMemberRepository.findById(id)).thenReturn(Optional.of(roleMember));

        // Act
        Optional<RoleMember> result = roleMemberDefaultService.find(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roleMember, result.get());
    }

    @Test
    void testFindByMemberAndRole() {
        // Arrange
        Member member = new Member();
        Role role = new Role();
        RoleMember roleMember = new RoleMember();
        when(roleMemberRepository.findByMemberAndRole(member, role)).thenReturn(Optional.of(roleMember));

        // Act
        Optional<RoleMember> result = roleMemberDefaultService.findByMemberAndRole(member, role);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(roleMember, result.get());
    }

    @Test
    void testFindAll() {
        // Arrange
        RoleMember roleMember1 = new RoleMember();
        RoleMember roleMember2 = new RoleMember();
        List<RoleMember> roleMembers = Arrays.asList(roleMember1, roleMember2);
        when(roleMemberRepository.findAll()).thenReturn(roleMembers);

        // Act
        List<RoleMember> result = roleMemberDefaultService.findAll();

        // Assert
        assertEquals(roleMembers, result);
    }

    @Test
    void testFindAllWithPageable() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        RoleMember roleMember1 = new RoleMember();
        RoleMember roleMember2 = new RoleMember();
        Page<RoleMember> page = new PageImpl<>(Arrays.asList(roleMember1, roleMember2));
        when(roleMemberRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<RoleMember> result = roleMemberDefaultService.findAll(pageable);

        // Assert
        assertEquals(page, result);
    }

    @Test
    void testFindAllByMember() {
        // Arrange
        Member member = new Member();
        Pageable pageable = PageRequest.of(0, 10);
        RoleMember roleMember1 = new RoleMember();
        RoleMember roleMember2 = new RoleMember();
        Page<RoleMember> page = new PageImpl<>(Arrays.asList(roleMember1, roleMember2));
        when(roleMemberRepository.findAllByMember(member, pageable)).thenReturn(page);

        // Act
        Page<RoleMember> result = roleMemberDefaultService.findAllByMember(member, pageable);

        // Assert
        assertEquals(page, result);
    }

    @Test
    void testFindAllByRole() {
        // Arrange
        Role role = new Role();
        Pageable pageable = PageRequest.of(0, 10);
        RoleMember roleMember1 = new RoleMember();
        RoleMember roleMember2 = new RoleMember();
        Page<RoleMember> page = new PageImpl<>(Arrays.asList(roleMember1, roleMember2));
        when(roleMemberRepository.findAllByRole(role, pageable)).thenReturn(page);

        // Act
        Page<RoleMember> result = roleMemberDefaultService.findAllByRole(role, pageable);

        // Assert
        assertEquals(page, result);
    }

    @Test
    void testCreate() {
        // Arrange
        RoleMember roleMember = new RoleMember();

        // Act
        roleMemberDefaultService.create(roleMember);

        // Assert
        verify(roleMemberRepository, times(1)).save(roleMember);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        RoleMember roleMember = new RoleMember();
        when(roleMemberRepository.findById(id)).thenReturn(Optional.of(roleMember));

        // Act
        roleMemberDefaultService.delete(id);

        // Assert
        verify(roleMemberRepository, times(1)).delete(roleMember);
    }

    @Test
    void testUpdate() {
        // Arrange
        RoleMember roleMember = new RoleMember();

        // Act
        roleMemberDefaultService.update(roleMember);

        // Assert
        verify(roleMemberRepository, times(1)).save(roleMember);
    }
}
