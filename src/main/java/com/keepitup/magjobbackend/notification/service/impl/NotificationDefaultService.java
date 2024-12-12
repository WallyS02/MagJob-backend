package com.keepitup.magjobbackend.notification.service.impl;

import com.keepitup.magjobbackend.configuration.Constants;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.notification.respository.api.NotificationRepository;
import com.keepitup.magjobbackend.notification.service.api.NotificationService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationDefaultService implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationDefaultService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
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

        sendNotificationToWebSocket(notification);

        return notificationRepository.save(notification);
    }

    @Override
    public void sendNotificationToWebSocket(Notification notification) {
        String destination;

        if (notification.getUser() != null) {
            destination = String.join(
                    "",
                    Constants.NOTIFICATION_USER_DEFAULT_WEBSOCKET_ENDPOINT,
                    notification.getUser().getId().toString(),
                    Constants.NOTIFICATION_ENDPOINT
            );
        } else if (notification.getMember() != null) {
            destination = String.join(
                    "",
                    Constants.NOTIFICATION_MEMBER_DEFAULT_WEBSOCKET_ENDPOINT,
                    notification.getMember().getId().toString(),
                    Constants.NOTIFICATION_ENDPOINT
            );
        } else if (notification.getOrganization() != null) {
            destination = String.join(
                    "",
                    Constants.NOTIFICATION_ORGANIZATION_DEFAULT_WEBSOCKET_ENDPOINT,
                    notification.getOrganization().getId().toString(),
                    Constants.NOTIFICATION_ENDPOINT
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

      //  messagingTemplate.convertAndSend(destination, notification);
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
