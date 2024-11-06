package com.keepitup.magjobbackend.notification.function;

import com.keepitup.magjobbackend.notification.dto.GetNotificationsResponse;
import com.keepitup.magjobbackend.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class NotificationsToResponseFunction implements BiFunction<Page<Notification>, Integer, GetNotificationsResponse> {
    public GetNotificationsResponse apply(Page<Notification> notifications, Integer count) {
        return GetNotificationsResponse.builder()
                .notifications(notifications.stream()
                        .map(notification -> GetNotificationsResponse.Notification.builder()
                                .id(notification.getId())
                                .content(notification.getContent())
                                .seen(notification.isSeen())
                                .sent(notification.isSent())
                                .organizationId(notification.getOrganization() != null ? notification.getOrganization().getId() : null)
                                .memberId(notification.getMember() != null ? notification.getMember().getId() : null)
                                .userId(notification.getUser() != null ? notification.getUser().getId() : null)
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}