package com.keepitup.magjobbackend.announcement.service.api;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    Optional<Announcement> find(BigInteger id);

    List<Announcement> findAll();

    Page<Announcement> findAll(Pageable pageable);

    Optional<Announcement> findByTitle(String title);

    Page<Announcement> findAllByContent(String content, Pageable pageable);

    Page<Announcement> findAllByDateOfExpiration(ZonedDateTime dateOfExpiration, Pageable pageable);

    Page<Announcement> findAllByOrganization(Organization organization, Pageable pageable);

    void create(Announcement announcement);

    void delete(BigInteger id);

    void update(Announcement announcement);
}
