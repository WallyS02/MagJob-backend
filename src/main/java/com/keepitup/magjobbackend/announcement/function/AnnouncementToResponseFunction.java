package com.keepitup.magjobbackend.announcement.function;

import com.keepitup.magjobbackend.announcement.dto.GetAnnouncementResponse;
import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.member.dto.GetMemberResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnnouncementToResponseFunction implements Function<Announcement, GetAnnouncementResponse> {
    @Override
    public GetAnnouncementResponse apply(Announcement announcement) {
        return GetAnnouncementResponse.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .dateOfExpiration(announcement.getDateOfExpiration())
                .organization(GetAnnouncementResponse.Organization.builder()
                        .id(announcement.getOrganization().getId())
                        .name(announcement.getOrganization().getName())
                        .build())
                .build();
    }
}
