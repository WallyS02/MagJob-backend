package com.keepitup.magjobbackend.announcementreceiver.repository.api;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AnnouncementReceiverRepository  extends JpaRepository<AnnouncementReceiver, BigInteger> {
    Page<AnnouncementReceiver> findAllByMember(Member member, Pageable pageable);

    Page<AnnouncementReceiver> findAllByAnnouncement(Announcement announcement, Pageable pageable);

    Optional<AnnouncementReceiver> findByMemberAndAnnouncement(Member member, Announcement announcement);
}
