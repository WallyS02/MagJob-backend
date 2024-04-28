package com.keepitup.magjobbackend.announcement.function;

import com.keepitup.magjobbackend.announcement.dto.GetAnnouncementsResponse;
import com.keepitup.magjobbackend.announcement.entity.Announcement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class AnnouncementsToResponseFunction implements Function<List<Announcement>, GetAnnouncementsResponse> {
    @Override
    public GetAnnouncementsResponse apply(List<Announcement> announcements) {
        return GetAnnouncementsResponse.builder()
                .announcements(announcements.stream()
                        .map(announcement -> GetAnnouncementsResponse.Announcement.builder()
                                .id(announcement.getId())
                                .title(announcement.getTitle())
                                .content(announcement.getContent())
                                .organizationId(announcement.getOrganization().getId())
                                .build())
                        .toList())
                .build();
    }
}
