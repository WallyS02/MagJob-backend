package com.keepitup.magjobbackend.announcementreceiver.service.impl;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import com.keepitup.magjobbackend.announcementreceiver.repository.api.AnnouncementReceiverRepository;
import com.keepitup.magjobbackend.announcementreceiver.service.api.AnnouncementReceiverService;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<AnnouncementReceiver> findAll(Pageable pageable) {
        return announcementReceiverRepository.findAll(pageable);
    }

    @Override
    public Page<AnnouncementReceiver> findAllByMember(Member member, Pageable pageable) {
        return announcementReceiverRepository.findAllByMember(member, pageable);
    }

    @Override
    public Page<AnnouncementReceiver> findAllByAnnouncement(Announcement announcement, Pageable pageable) {
        return announcementReceiverRepository.findAllByAnnouncement(announcement, pageable);
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
