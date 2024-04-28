package com.keepitup.magjobbackend.announcement.service.impl;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcement.repository.api.AnnouncementRepository;
import com.keepitup.magjobbackend.announcement.service.api.AnnouncementService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementDefaultService implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementDefaultService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Optional<Announcement> find(BigInteger id) {
        return announcementRepository.findById(id);
    }

    @Override
    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    @Override
    public Optional<Announcement> findByTitle(String title) {
        return announcementRepository.findByTitle(title);
    }

    @Override
    public List<Announcement> findAllByContent(String content) {
        return announcementRepository.findAllByContent(content);
    }

    @Override
    public List<Announcement> findAllByDateOfExpiration(ZonedDateTime dateOfExpiration) {
        return announcementRepository.findAllByDateOfExpiration(dateOfExpiration);
    }

    @Override
    public List<Announcement> findAllByOrganization(Organization organization) {
        return announcementRepository.findAllByOrganization(organization);
    }

    @Override
    public void create(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    @Override
    public void delete(BigInteger id) {
        announcementRepository.findById(id).ifPresent(announcementRepository::delete);
    }

    @Override
    public void update(Announcement announcement) {
        announcementRepository.save(announcement);
    }
}
