package com.keepitup.magjobbackend.notification.service.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.notification.respository.api.NotificationRepository;
import com.keepitup.magjobbackend.notification.service.api.NotificationService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationDefaultService implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationDefaultService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> find(BigInteger id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public Page<Notification> findAllBySeen(boolean seen, Pageable pageable) {
        return notificationRepository.findAllBySeen(seen, pageable);
    }

    @Override
    public Page<Notification> findAllBySent(boolean sent, Pageable pageable) {
        return notificationRepository.findAllBySent(sent, pageable);
    }

    @Override
    public Page<Notification> findAllByOrganization(Organization organization, Pageable pageable) {
        return notificationRepository.findAllByOrganization(organization, pageable);
    }

    @Override
    public Page<Notification> findAllByOrganizationAndSeen(Organization organization, Boolean seen, Pageable pageable) {
        return notificationRepository.findAllByOrganizationAndSeen(organization, seen, pageable);
    }

    @Override
    public Page<Notification> findAllByMember(Member member, Pageable pageable) {
        return notificationRepository.findAllByMember(member, pageable);
    }

    @Override
    public Page<Notification> findAllByMemberAndSeen(Member member, Boolean seen, Pageable pageable) {
        return notificationRepository.findAllByMemberAndSeen(member, seen, pageable);
    }

    @Override
    public Page<Notification> findAllByUser(User user, Pageable pageable) {
        return notificationRepository.findAllByUser(user, pageable);
    }

    @Override
    public Page<Notification> findAllByUserAndSeen(User user, Boolean seen, Pageable pageable) {
        return notificationRepository.findAllByUserAndSeen(user, seen, pageable);
    }

    @Override
    public Notification create(Notification notification) {
        notification.setDateOfCreation(LocalDateTime.now());
        notification.setSeen(false);
        notification.setSent(false);
        return notificationRepository.save(notification);
    }

    @Override
    public void update(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void delete(BigInteger id) {
        notificationRepository.findById(id).ifPresent(notificationRepository::delete);
    }
}
