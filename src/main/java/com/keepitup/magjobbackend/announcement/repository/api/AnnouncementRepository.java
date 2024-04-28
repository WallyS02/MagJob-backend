package com.keepitup.magjobbackend.announcement.repository.api;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, BigInteger> {
    Optional<Announcement> findByTitle(String title);

    List<Announcement> findAllByContent(String content);

    List<Announcement> findAllByDateOfExpiration(ZonedDateTime dateOfExpiration);

    List<Announcement> findAllByOrganization(Organization organization);
}
