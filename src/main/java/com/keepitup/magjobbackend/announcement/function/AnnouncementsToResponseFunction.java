package com.keepitup.magjobbackend.announcement.function;

import com.keepitup.magjobbackend.announcement.dto.GetAnnouncementsResponse;
import com.keepitup.magjobbackend.announcement.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AnnouncementsToResponseFunction implements BiFunction<Page<Announcement>, Integer, GetAnnouncementsResponse> {
    @Override
    public GetAnnouncementsResponse apply(Page<Announcement> announcements, Integer count) {
        return GetAnnouncementsResponse.builder()
                .announcements(announcements.stream()
                        .map(announcement -> GetAnnouncementsResponse.Announcement.builder()
                                .id(announcement.getId())
                                .title(announcement.getTitle())
                                .content(announcement.getContent())
                                .organizationId(announcement.getOrganization().getId())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
