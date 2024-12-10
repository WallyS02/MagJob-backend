package com.keepitup.magjobbackend.notification.function;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.repository.api.MemberRepository;
import com.keepitup.magjobbackend.notification.dto.PostNotificationRequest;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.notification.entity.Notification.NotificationBuilder;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

@Component
public class RequestToNotificationFunction implements Function<PostNotificationRequest, Notification> {

    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    @Autowired
    public RequestToNotificationFunction(
            OrganizationRepository organizationRepository,
            MemberRepository memberRepository,
            UserRepository userRepository
    ) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification apply(PostNotificationRequest postNotificationRequest) {
        NotificationBuilder notificationBuilder = Notification.builder()
                .content(postNotificationRequest.getContent());

        if (postNotificationRequest.getOrganization() != null) {
            Organization organization = organizationRepository.findById(postNotificationRequest.getOrganization())
                    .orElse(null);
            notificationBuilder.organization(organization);
        }

        if (postNotificationRequest.getMember() != null) {
            Member member = memberRepository.findById(postNotificationRequest.getMember())
                    .orElse(null);
            notificationBuilder.member(member);
        }

        if (postNotificationRequest.getUser() != null) {
            User user = userRepository.findById(postNotificationRequest.getUser())
                    .orElse(null);
            notificationBuilder.user(user);
        }

        return notificationBuilder.build();
    }
}
