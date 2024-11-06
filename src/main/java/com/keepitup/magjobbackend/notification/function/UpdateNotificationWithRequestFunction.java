package com.keepitup.magjobbackend.notification.function;

import com.keepitup.magjobbackend.notification.dto.PatchNotificationRequest;
import com.keepitup.magjobbackend.notification.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateNotificationWithRequestFunction implements BiFunction<Notification, PatchNotificationRequest, Notification> {
    @Override
    public Notification apply(Notification notification, PatchNotificationRequest patchNotificationRequest) {
        return Notification.builder()
                .id(notification.getId())
                .content(notification.getContent())
                .seen(patchNotificationRequest.isSeen())
                .sent(patchNotificationRequest.isSent())
                .organization(notification.getOrganization())
                .member(notification.getMember())
                .user(notification.getUser())
                .dateOfCreation(notification.getDateOfCreation())
                .build();
    }
}
