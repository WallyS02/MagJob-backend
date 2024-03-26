package com.keepitup.magjobbackend.announcement.service.api;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.organization.entity.Organization;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    Optional<Announcement> find(BigInteger id);

    List<Announcement> findAll();

    Optional<Announcement> findByTitle(String title);

    List<Announcement> findAllByContent(String content);

    List<Announcement> findAllByDateOfExpiration(ZonedDateTime dateOfExpiration);

    List<Announcement> findAllByOrganization(Organization organization);

    void create(Announcement announcement);

    void delete(BigInteger id);

    void update(Announcement announcement);
}
