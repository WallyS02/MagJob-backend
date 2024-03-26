package com.keepitup.magjobbackend.announcement.function;

import com.keepitup.magjobbackend.announcement.dto.PostAnnouncementRequest;
import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToAnnouncementFunction implements Function<PostAnnouncementRequest, Announcement> {
    @Override
    public Announcement apply(PostAnnouncementRequest request) {
        return Announcement.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .dateOfExpiration(request.getDateOfExpiration())
                .organization(Organization.builder()
                        .id(request.getOrganization())
                        .build())
                .build();
    }
}
