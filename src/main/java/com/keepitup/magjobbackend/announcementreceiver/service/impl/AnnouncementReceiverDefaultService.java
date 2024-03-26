package com.keepitup.magjobbackend.announcementreceiver.service.impl;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import com.keepitup.magjobbackend.announcementreceiver.repository.api.AnnouncementReceiverRepository;
import com.keepitup.magjobbackend.announcementreceiver.service.api.AnnouncementReceiverService;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementReceiverDefaultService implements AnnouncementReceiverService {
    private final AnnouncementReceiverRepository announcementReceiverRepository;

    @Autowired
    public AnnouncementReceiverDefaultService(AnnouncementReceiverRepository announcementReceiverRepository) {
        this.announcementReceiverRepository = announcementReceiverRepository;
    }

    @Override
    public Optional<AnnouncementReceiver> find(BigInteger id) {
        return announcementReceiverRepository.findById(id);
    }

    @Override
    public List<AnnouncementReceiver> findAll() {
        return announcementReceiverRepository.findAll();
    }

    @Override
    public List<AnnouncementReceiver> findAllByMember(Member member) {
        return announcementReceiverRepository.findAllByMember(member);
    }

    @Override
    public List<AnnouncementReceiver> findAllByAnnouncement(Announcement announcement) {
        return announcementReceiverRepository.findAllByAnnouncement(announcement);
    }

    @Override
    public Optional<AnnouncementReceiver> findByMemberAndAnnouncement(Member member, Announcement announcement) {
        return announcementReceiverRepository.findByMemberAndAnnouncement(member, announcement);
    }

    @Override
    public void create(AnnouncementReceiver announcementReceiver) {
        announcementReceiverRepository.save(announcementReceiver);
    }

    @Override
    public void delete(BigInteger id) {
        announcementReceiverRepository.findById(id).ifPresent(announcementReceiverRepository::delete);
    }

    @Override
    public void update(AnnouncementReceiver announcementReceiver) {
        announcementReceiverRepository.save(announcementReceiver);
    }
}
