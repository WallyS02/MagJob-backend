package com.keepitup.magjobbackend.announcement.function;

import com.keepitup.magjobbackend.announcement.dto.PatchAnnouncementRequest;
import com.keepitup.magjobbackend.announcement.entity.Announcement;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateAnnouncementWithRequestFunction implements BiFunction<Announcement, PatchAnnouncementRequest, Announcement> {
    @Override
    public Announcement apply(Announcement announcement, PatchAnnouncementRequest request) {
        return Announcement.builder()
                .id(announcement.getId())
                .title(request.getTitle())
                .content(request.getContent())
                .dateOfExpiration(request.getDateOfExpiration())
                .organization(announcement.getOrganization())
                .build();
    }
}
