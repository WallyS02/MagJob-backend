package com.keepitup.magjobbackend.announcementreceiver.service.api;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import com.keepitup.magjobbackend.member.entity.Member;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface AnnouncementReceiverService {
    Optional<AnnouncementReceiver> find(BigInteger id);

    List<AnnouncementReceiver> findAll();

    List<AnnouncementReceiver> findAllByMember(Member member);

    List<AnnouncementReceiver> findAllByAnnouncement(Announcement announcement);

    Optional<AnnouncementReceiver> findByMemberAndAnnouncement(Member member, Announcement announcement);

    void create(AnnouncementReceiver announcementReceiver);

    void delete(BigInteger id);

    void update(AnnouncementReceiver announcementReceiver);
}
