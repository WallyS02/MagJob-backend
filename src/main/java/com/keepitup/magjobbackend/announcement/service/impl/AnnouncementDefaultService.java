package com.keepitup.magjobbackend.announcement.service.impl;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcement.repository.api.AnnouncementRepository;
import com.keepitup.magjobbackend.announcement.service.api.AnnouncementService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Announcement> findAll(Pageable pageable) {
        return announcementRepository.findAll(pageable);
    }

    @Override
    public Optional<Announcement> findByTitle(String title) {
        return announcementRepository.findByTitle(title);
    }

    @Override
    public Page<Announcement> findAllByContent(String content, Pageable pageable) {
        return announcementRepository.findAllByContent(content, pageable);
    }

    @Override
    public Page<Announcement> findAllByDateOfExpiration(ZonedDateTime dateOfExpiration, Pageable pageable) {
        return announcementRepository.findAllByDateOfExpiration(dateOfExpiration, pageable);
    }

    @Override
    public Page<Announcement> findAllByOrganization(Organization organization, Pageable pageable) {
        return announcementRepository.findAllByOrganization(organization, pageable);
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
