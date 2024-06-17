package com.keepitup.magjobbackend.announcementreceiver.service.impl;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import com.keepitup.magjobbackend.announcementreceiver.repository.api.AnnouncementReceiverRepository;
import com.keepitup.magjobbackend.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AnnouncementReceiverDefaultServiceTest {

    private AnnouncementReceiverDefaultService announcementReceiverService;

    @Mock
    private AnnouncementReceiverRepository announcementReceiverRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        announcementReceiverService = new AnnouncementReceiverDefaultService(announcementReceiverRepository);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        AnnouncementReceiver expectedAnnouncementReceiver = new AnnouncementReceiver();
        when(announcementReceiverRepository.findById(id)).thenReturn(Optional.of(expectedAnnouncementReceiver));

        // Act
        Optional<AnnouncementReceiver> result = announcementReceiverService.find(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedAnnouncementReceiver, result.get());
        verify(announcementReceiverRepository, times(1)).findById(id);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<AnnouncementReceiver> expectedList = new ArrayList<>();
        expectedList.add(new AnnouncementReceiver());
        expectedList.add(new AnnouncementReceiver());
        when(announcementReceiverRepository.findAll()).thenReturn(expectedList);

        // Act
        List<AnnouncementReceiver> result = announcementReceiverService.findAll();

        // Assert
        assertEquals(expectedList.size(), result.size());
        assertEquals(expectedList, result);
        verify(announcementReceiverRepository, times(1)).findAll();
    }

    @Test
    void testFindAllByMember() {
        // Arrange
        Member member = new Member();
        Page<AnnouncementReceiver> announcementReceivers = new PageImpl<>(List.of(new AnnouncementReceiver()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(announcementReceiverRepository.findAllByMember(member, pageRequest)).thenReturn(announcementReceivers);

        // Act
        Page<AnnouncementReceiver> result = announcementReceiverService.findAllByMember(member, pageRequest);

        // Assert
        assertEquals(announcementReceivers, result);
        verify(announcementReceiverRepository, times(1)).findAllByMember(member, pageRequest);
    }

    @Test
    void testFindAllByAnnouncement() {
        // Arrange
        Announcement announcement = new Announcement();
        Page<AnnouncementReceiver> announcementReceivers = new PageImpl<>(List.of(new AnnouncementReceiver()));
        PageRequest pageRequest = PageRequest.of(0, 1);
        when(announcementReceiverRepository.findAllByAnnouncement(announcement, pageRequest)).thenReturn(announcementReceivers);

        // Act
        Page<AnnouncementReceiver> result = announcementReceiverService.findAllByAnnouncement(announcement, pageRequest);

        // Assert
        assertEquals(announcementReceivers, result);
        verify(announcementReceiverRepository, times(1)).findAllByAnnouncement(announcement, pageRequest);
    }

    @Test
    void testFindByMemberAndAnnouncement() {
        // Arrange
        Member member = new Member();
        Announcement announcement = new Announcement();
        AnnouncementReceiver expectedAnnouncementReceiver = new AnnouncementReceiver();
        when(announcementReceiverRepository.findByMemberAndAnnouncement(member, announcement)).thenReturn(Optional.of(expectedAnnouncementReceiver));

        // Act
        Optional<AnnouncementReceiver> result = announcementReceiverService.findByMemberAndAnnouncement(member, announcement);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedAnnouncementReceiver, result.get());
        verify(announcementReceiverRepository, times(1)).findByMemberAndAnnouncement(member, announcement);
    }

    @Test
    void testCreate() {
        // Arrange
        AnnouncementReceiver announcementReceiver = new AnnouncementReceiver();

        // Act
        announcementReceiverService.create(announcementReceiver);

        // Assert
        verify(announcementReceiverRepository, times(1)).save(announcementReceiver);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        AnnouncementReceiver announcementReceiver = new AnnouncementReceiver();
        when(announcementReceiverRepository.findById(id)).thenReturn(Optional.of(announcementReceiver));

        // Act
        announcementReceiverService.delete(id);

        // Assert
        verify(announcementReceiverRepository, times(1)).findById(id);
        verify(announcementReceiverRepository, times(1)).delete(announcementReceiver);
    }

    @Test
    void testUpdate() {
        // Arrange
        AnnouncementReceiver announcementReceiver = new AnnouncementReceiver();

        // Act
        announcementReceiverService.update(announcementReceiver);

        // Assert
        verify(announcementReceiverRepository, times(1)).save(announcementReceiver);
    }
}
