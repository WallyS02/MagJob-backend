package com.keepitup.magjobbackend.announcement.service.impl;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcement.repository.api.AnnouncementRepository;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AnnouncementDefaultServiceTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private AnnouncementDefaultService announcementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        Announcement announcement = new Announcement();
        when(announcementRepository.findById(id)).thenReturn(Optional.of(announcement));

        // Act
        Optional<Announcement> result = announcementService.find(id);

        // Assert
        assertEquals(Optional.of(announcement), result);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement());
        announcements.add(new Announcement());
        when(announcementRepository.findAll()).thenReturn(announcements);

        // Act
        List<Announcement> result = announcementService.findAll();

        // Assert
        assertEquals(announcements.size(), result.size());
        assertEquals(announcements, result);
        verify(announcementRepository, times(1)).findAll();
    }

    @Test
    void testFindByTitle() {
        // Arrange
        String title = "Test Announcement";
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        when(announcementRepository.findByTitle(title)).thenReturn(Optional.of(announcement));

        // Act
        Optional<Announcement> result = announcementService.findByTitle(title);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(title, result.get().getTitle());
        verify(announcementRepository, times(1)).findByTitle(title);
    }

    @Test
    void testFindAllByContent() {
        // Arrange
        String content = "Test Content";
        Announcement announcement1 = new Announcement();
        announcement1.setContent(content);
        Announcement announcement2 = new Announcement();
        announcement2.setContent(content);
        List<Announcement> announcements = Arrays.asList(announcement1, announcement2);
        when(announcementRepository.findAllByContent(content)).thenReturn(announcements);

        // Act
        List<Announcement> result = announcementService.findAllByContent(content);

        // Assert
        assertEquals(announcements.size(), result.size());
        assertTrue(result.contains(announcement1));
        assertTrue(result.contains(announcement2));
        verify(announcementRepository, times(1)).findAllByContent(content);
    }

    @Test
    void testFindAllByDateOfExpiration() {
        // Arrange
        ZonedDateTime expirationDate = ZonedDateTime.now().plusDays(1); // Zakładamy, że ogłoszenia wygasają jutro
        Announcement announcement1 = new Announcement();
        announcement1.setDateOfExpiration(expirationDate);
        Announcement announcement2 = new Announcement();
        announcement2.setDateOfExpiration(expirationDate);
        List<Announcement> announcements = Arrays.asList(announcement1, announcement2);
        when(announcementRepository.findAllByDateOfExpiration(expirationDate)).thenReturn(announcements);

        // Act
        List<Announcement> result = announcementService.findAllByDateOfExpiration(expirationDate);

        // Assert
        assertEquals(announcements.size(), result.size());
        assertTrue(result.contains(announcement1));
        assertTrue(result.contains(announcement2));
        verify(announcementRepository, times(1)).findAllByDateOfExpiration(expirationDate);
    }

    @Test
    void testFindAllByOrganization() {
        // Arrange
        Organization organization = new Organization();
        organization.setId(BigInteger.ONE);

        Announcement announcement1 = new Announcement();
        announcement1.setOrganization(organization);
        Announcement announcement2 = new Announcement();
        announcement2.setOrganization(organization);

        List<Announcement> announcements = Arrays.asList(announcement1, announcement2);

        when(announcementRepository.findAllByOrganization(organization)).thenReturn(announcements);

        // Act
        List<Announcement> result = announcementService.findAllByOrganization(organization);

        // Assert
        assertEquals(announcements.size(), result.size());
        assertTrue(result.contains(announcement1));
        assertTrue(result.contains(announcement2));
        verify(announcementRepository, times(1)).findAllByOrganization(organization);
    }

    @Test
    void testCreate() {
        // Arrange
        Announcement announcement = new Announcement();

        // Act
        announcementService.create(announcement);

        // Assert
        verify(announcementRepository, times(1)).save(announcement);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger id = BigInteger.ONE;
        Announcement announcement = new Announcement();
        when(announcementRepository.findById(id)).thenReturn(Optional.of(announcement));

        // Act
        announcementService.delete(id);

        // Assert
        verify(announcementRepository, times(1)).findById(id);
        verify(announcementRepository, times(1)).delete(announcement);
    }

    @Test
    void testUpdate() {
        // Arrange
        Announcement announcement = new Announcement();
        when(announcementRepository.save(announcement)).thenReturn(announcement);

        // Act
        announcementService.update(announcement);

        // Assert
        verify(announcementRepository, times(1)).save(announcement);
    }
}
