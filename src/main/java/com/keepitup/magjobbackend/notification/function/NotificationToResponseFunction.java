package com.keepitup.magjobbackend.notification.function;

import com.keepitup.magjobbackend.notification.dto.GetNotificationResponse;
import com.keepitup.magjobbackend.notification.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NotificationToResponseFunction implements Function<Notification, GetNotificationResponse> {
    @Override
    public GetNotificationResponse apply(Notification notification) {
        return GetNotificationResponse.builder()
                .id(notification.getId())
                .content(notification.getContent())
                .seen(notification.isSeen())
                .sent(notification.isSent())
                .organization(notification.getOrganization() != null ? GetNotificationResponse.Organization.builder()
                        .id(notification.getOrganization().getId())
                        .name(notification.getOrganization().getName())
                        .build() : null)
                .member(notification.getMember() != null ? GetNotificationResponse.Member.builder()
                        .id(notification.getMember().getId())
                        .pseudonym(notification.getMember().getPseudonym())
                        .build() : null)
                .user(notification.getUser() != null ? GetNotificationResponse.User.builder()
                        .id(notification.getUser().getId())
                        .email(notification.getUser().getEmail())
                        .build() : null)
                .build();
    }
}