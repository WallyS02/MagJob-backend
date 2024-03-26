package com.keepitup.magjobbackend.announcementreceiver.repository.api;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementReceiverRepository  extends JpaRepository<AnnouncementReceiver, BigInteger> {
    List<AnnouncementReceiver> findAllByMember(Member member);

    List<AnnouncementReceiver> findAllByAnnouncement(Announcement announcement);

    Optional<AnnouncementReceiver> findByMemberAndAnnouncement(Member member, Announcement announcement);
}
