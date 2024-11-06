package com.keepitup.magjobbackend.notification.controller.impl;

import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.impl.MemberDefaultService;
import com.keepitup.magjobbackend.notification.controller.api.NotificationController;
import com.keepitup.magjobbackend.notification.dto.GetNotificationResponse;
import com.keepitup.magjobbackend.notification.dto.GetNotificationsResponse;
import com.keepitup.magjobbackend.notification.dto.PatchNotificationRequest;
import com.keepitup.magjobbackend.notification.dto.PostNotificationRequest;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.notification.function.NotificationToResponseFunction;
import com.keepitup.magjobbackend.notification.function.NotificationsToResponseFunction;
import com.keepitup.magjobbackend.notification.function.RequestToNotificationFunction;
import com.keepitup.magjobbackend.notification.function.UpdateNotificationWithRequestFunction;
import com.keepitup.magjobbackend.notification.service.impl.NotificationDefaultService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.impl.OrganizationDefaultService;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.service.impl.UserDefaultService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

@Controller
@Log
public class NotificationDefaultController implements NotificationController {
    private final NotificationDefaultService notificationService;
    private final OrganizationDefaultService organizationService;
    private final MemberDefaultService memberService;
    private final UserDefaultService userService;
    private final SecurityService securityService;
    private final NotificationToResponseFunction notificationToResponseFunction;
    private final NotificationsToResponseFunction notificationsToResponseFunction;
    private final RequestToNotificationFunction requestToNotificationFunction;
    private final UpdateNotificationWithRequestFunction updateNotificationWithRequestFunction;

    @Autowired
    public NotificationDefaultController(
        NotificationDefaultService notificationService,
        OrganizationDefaultService organizationService,
        MemberDefaultService memberService,
        UserDefaultService userService,
        SecurityService securityService,
        NotificationToResponseFunction notificationToResponseFunction,
        NotificationsToResponseFunction notificationsToResponseFunction,
        RequestToNotificationFunction requestToNotificationFunction,
        UpdateNotificationWithRequestFunction updateNotificationWithRequestFunction
    ) {
        this.notificationService = notificationService;
        this.organizationService = organizationService;
        this.memberService = memberService;
        this.userService = userService;
        this.securityService = securityService;
        this.notificationToResponseFunction = notificationToResponseFunction;
        this.notificationsToResponseFunction = notificationsToResponseFunction;
        this.requestToNotificationFunction = requestToNotificationFunction;
        this.updateNotificationWithRequestFunction = updateNotificationWithRequestFunction;
    }

    @Override
    public GetNotificationsResponse getNotifications(int page, int size) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = notificationService.findAll().size();
        return notificationsToResponseFunction.apply(notificationService.findAll(pageRequest), count);
    }

    @Override
    public GetNotificationsResponse getSeenOrUnseenNotifications(int page, int size, boolean seen) {
        if (!securityService.hasAdminPermission()) {
            System.out.println(securityService.hasAdminPermission());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        System.out.println("co?");

        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = notificationService.findAllBySeen(seen, Pageable.unpaged()).getNumberOfElements();
        return notificationsToResponseFunction.apply(notificationService.findAllBySeen(seen, pageRequest), count);
    }

    @Override
    public GetNotificationsResponse getSentOrNotSentNotifications(int page, int size, boolean sent) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = notificationService.findAllBySent(sent, Pageable.unpaged()).getNumberOfElements();
        return notificationsToResponseFunction.apply(notificationService.findAllBySent(sent, pageRequest), count);
    }

    @Override
    public GetNotificationResponse getNotification(BigInteger id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Notification notification = notificationService.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return notificationToResponseFunction.apply(notification);
    }

    @Override
    public GetNotificationsResponse getNotificationsByOrganization(int page, int size, BigInteger organizationId) {
        Organization organization = organizationService.find(organizationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.belongsToOrganization(organization) && !securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Integer count = notificationService.findAllByOrganization(organization, Pageable.unpaged()).getNumberOfElements();

        return notificationsToResponseFunction.apply(notificationService.findAllByOrganization(organization, pageRequest), count);
    }

    @Override
    public GetNotificationsResponse getSeenOrUnseenNotificationsByOrganization(int page, int size, BigInteger organizationId, boolean seen) {
        Organization organization = organizationService.find(organizationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.belongsToOrganization(organization) && !securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Integer count = notificationService.findAllByOrganizationAndSeen(organization, seen, Pageable.unpaged()).getNumberOfElements();

        return notificationsToResponseFunction.apply(notificationService.findAllByOrganizationAndSeen(organization, seen, pageRequest), count);
    }

    @Override
    public GetNotificationsResponse getNotificationsByMember(int page, int size, BigInteger memberId) {
        Member member = memberService.find(memberId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isCurrentMember(member) && !securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Integer count = notificationService.findAllByMember(member, Pageable.unpaged()).getNumberOfElements();

        return notificationsToResponseFunction.apply(notificationService.findAllByMember(member, pageRequest), count);
    }

    @Override
    public GetNotificationsResponse getSeenOrUnseenNotificationsByMember(int page, int size, BigInteger memberId, boolean seen) {
        Member member = memberService.find(memberId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isCurrentMember(member) && !securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Integer count = notificationService.findAllByMemberAndSeen(member, seen, Pageable.unpaged()).getNumberOfElements();

        return notificationsToResponseFunction.apply(notificationService.findAllByMemberAndSeen(member, seen, pageRequest), count);
    }

    @Override
    public GetNotificationsResponse getNotificationsByUser(int page, int size, UUID userId) {
        User user = userService.find(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isCurrentUser(user) && !securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Integer count = notificationService.findAllByUser(user, Pageable.unpaged()).getNumberOfElements();

        return notificationsToResponseFunction.apply(notificationService.findAllByUser(user, pageRequest), count);
    }

    @Override
    public GetNotificationsResponse getSeenOrUnseenNotificationsByUser(int page, int size, UUID userId, boolean seen) {
        User user = userService.find(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isCurrentUser(user) && !securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Integer count = notificationService.findAllByUserAndSeen(user, seen, Pageable.unpaged()).getNumberOfElements();

        return notificationsToResponseFunction.apply(notificationService.findAllByUserAndSeen(user, seen, pageRequest), count);
    }

    @Override
    public GetNotificationResponse createNotification(PostNotificationRequest postNotificationRequest) {
        boolean hasOrganization = postNotificationRequest.getOrganization() != null;
        boolean hasMember = postNotificationRequest.getMember() != null;
        boolean hasUser = postNotificationRequest.getUser() != null;

        int targetCount = (hasOrganization ? 1 : 0) + (hasMember ? 1 : 0) + (hasUser ? 1 : 0);
        if (targetCount > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (hasUser) {
            User user = userService.find(postNotificationRequest.getUser()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );

            if (!securityService.hasAdminPermission() && !securityService.isCurrentUser(user)
                && !securityService.isAnyMember()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else if (hasOrganization) {
            Organization organization = organizationService.find(postNotificationRequest.getOrganization()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );

            if (!securityService.hasAdminPermission() && !securityService.belongsToOrganization(organization)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else if (hasMember) {
            Member member = memberService.find(postNotificationRequest.getMember()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
            );

            if (!securityService.hasAdminPermission() && !securityService.belongsToOrganization(member.getOrganization())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        Notification notification = notificationService.create(requestToNotificationFunction.apply(postNotificationRequest));

        if (notification == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return notificationToResponseFunction.apply(notification);
    }

    @Override
    public GetNotificationResponse updateNotificationAsSeen(BigInteger id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Notification notification = notificationService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        PatchNotificationRequest patchNotificationRequest = new PatchNotificationRequest();
        patchNotificationRequest.setSeen(true);
        patchNotificationRequest.setSent(notification.isSent());

        notificationService.update(updateNotificationWithRequestFunction.apply(notification, patchNotificationRequest));

        return notificationService.find(id)
                .map(notificationToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetNotificationResponse updateNotificationAsSent(BigInteger id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Notification notification = notificationService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        PatchNotificationRequest patchNotificationRequest = new PatchNotificationRequest();
        patchNotificationRequest.setSeen(notification.isSeen());
        patchNotificationRequest.setSent(true);

        notificationService.update(updateNotificationWithRequestFunction.apply(notification, patchNotificationRequest));

        return notificationService.find(id)
                .map(notificationToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteNotification(BigInteger id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        notificationService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        notificationService.delete(id);
    }
}
