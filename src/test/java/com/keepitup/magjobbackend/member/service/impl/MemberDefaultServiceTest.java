package com.keepitup.magjobbackend.member.service.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.repository.api.MemberRepository;
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
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberDefaultServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    private MemberDefaultService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberService = new MemberDefaultService(memberRepository, userRepository, organizationRepository);
    }

    @Test
    void testFindAllByIsStillMember() {
        // Arrange
        Boolean isStillMember = true;
        Page<Member> expectedMembers = new PageImpl<>(new ArrayList<>());
        when(memberRepository.findAllByIsStillMember(isStillMember, Pageable.unpaged())).thenReturn(expectedMembers);

        // Act
        Page<Member> result = memberService.findAllByIsStillMember(isStillMember, Pageable.unpaged());

        // Assert
        assertSame(expectedMembers, result);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger memberId = BigInteger.ONE;
        Member expectedMember = new Member();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(expectedMember));

        // Act
        Optional<Member> result = memberService.find(memberId);

        // Assert
        assertTrue(result.isPresent());
        assertSame(expectedMember, result.get());
    }

    @Test
    void testFindByIdAndIsStillMember() {
        // Arrange
        BigInteger memberId = BigInteger.ONE;
        Boolean isStillMember = true;
        Member expectedMember = new Member();
        when(memberRepository.findByIdAndIsStillMember(memberId, isStillMember)).thenReturn(Optional.of(expectedMember));

        // Act
        Optional<Member> result = memberService.findByIdAndIsStillMember(memberId, isStillMember);

        // Assert
        assertTrue(result.isPresent());
        assertSame(expectedMember, result.get());
    }

    @Test
    void testFindAllByPseudonym() {
        // Arrange
        String pseudonym = "john_doe";
        Page<Member> expectedMembers = new PageImpl<>(new ArrayList<>());
        when(memberRepository.findAllByPseudonym(pseudonym, Pageable.unpaged())).thenReturn(expectedMembers);

        // Act
        Page<Member> result = memberService.findAllByPseudonym(pseudonym, Pageable.unpaged());

        // Assert
        assertSame(expectedMembers, result);
    }

    @Test
    void testFindAllByOrganization() {
        // Arrange
        Organization organization = new Organization();
        Page<Member> expectedMembers = new PageImpl<>(new ArrayList<>());
        when(memberRepository.findAllByOrganization(organization, Pageable.unpaged())).thenReturn(expectedMembers);

        // Act
        Page<Member> result = memberService.findAllByOrganization(organization, Pageable.unpaged());

        // Assert
        assertSame(expectedMembers, result);
    }

    @Test
    void testFindAllByOrganizationAndIsStillMember() {
        // Arrange
        Organization organization = new Organization();
        Boolean isStillMember = true;
        Page<Member> expectedMembers = new PageImpl<>(new ArrayList<>());
        when(memberRepository.findAllByOrganizationAndIsStillMember(organization, isStillMember, Pageable.unpaged())).thenReturn(expectedMembers);

        // Act
        Page<Member> result = memberService.findAllByOrganizationAndIsStillMember(organization, isStillMember, Pageable.unpaged());

        // Assert
        assertSame(expectedMembers, result);
    }

    @Test
    void testCheckIfStillMember() {
        // Arrange
        BigInteger memberId = BigInteger.ONE;
        Member member = new Member();
        member.setIsStillMember(true);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // Act
        Boolean result = memberService.checkIfStillMember(memberId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testFindAllUsersByOrganization() {
        // Arrange
        BigInteger organizationId = BigInteger.ONE;
        Organization organization = new Organization();
        Page<Member> members = new PageImpl<>(new ArrayList<>());
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        when(memberRepository.findAllByOrganization(organization, Pageable.unpaged())).thenReturn(members);

        // Act
        Optional<Page<User>> result = memberService.findAllUsersByOrganization(organizationId, Pageable.unpaged());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(members.getTotalElements(), result.get().getTotalElements());
    }

    @Test
    void testFindAllOrganizationsByUser() {
        // Arrange
        BigInteger userId = BigInteger.ONE;
        User user = new User();
        Page<Member> members = new PageImpl<>(new ArrayList<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(memberRepository.findAllByUser(user, Pageable.unpaged())).thenReturn(members);

        // Act
        Optional<Page<Organization>> result = memberService.findAllOrganizationsByUser(userId, Pageable.unpaged());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(members.getTotalElements(), result.get().getTotalElements());
    }

    @Test
    void testFindByUserAndOrganization() {
        // Arrange
        User user = new User();
        Organization organization = new Organization();
        Member expectedMember = new Member();
        when(memberRepository.findByUserAndOrganization(user, organization)).thenReturn(Optional.of(expectedMember));

        // Act
        Optional<Member> result = memberService.findByUserAndOrganization(user, organization);

        // Assert
        assertTrue(result.isPresent());
        assertSame(expectedMember, result.get());
    }

    @Test
    void testCreate() {
        // Arrange
        Member member = new Member();

        // Act
        memberService.create(member);

        // Assert
        verify(memberRepository, times(1)).save(member);
        assertTrue(member.getIsStillMember());
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger memberId = BigInteger.ONE;
        Member member = new Member();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // Act
        memberService.delete(memberId);

        // Assert
        verify(memberRepository, times(1)).findById(memberId);
        assertFalse(member.getIsStillMember());
    }

    @Test
    void testUpdate() {
        // Arrange
        Member member = new Member();

        // Act
        memberService.update(member);

        // Assert
        verify(memberRepository, times(1)).save(member);
    }
}
