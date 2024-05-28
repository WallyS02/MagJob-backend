package com.keepitup.magjobbackend.announcement.repository.api;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, BigInteger> {
    Optional<Announcement> findByTitle(String title);

    Page<Announcement> findAllByContent(String content, Pageable pageable);

    Page<Announcement> findAllByDateOfExpiration(ZonedDateTime dateOfExpiration, Pageable pageable);

    Page<Announcement> findAllByOrganization(Organization organization, Pageable pageable);
}
