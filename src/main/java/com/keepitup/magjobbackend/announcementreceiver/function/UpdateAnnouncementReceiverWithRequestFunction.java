package com.keepitup.magjobbackend.announcementreceiver.function;

import com.keepitup.magjobbackend.announcement.dto.PatchAnnouncementRequest;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateAnnouncementReceiverWithRequestFunction implements BiFunction<AnnouncementReceiver, PatchAnnouncementRequest, AnnouncementReceiver> {
    @Override
    public AnnouncementReceiver apply(AnnouncementReceiver announcementReceiver, PatchAnnouncementRequest request) {
        return AnnouncementReceiver.builder()
                .id(announcementReceiver.getId())
                .member(announcementReceiver.getMember())
                .announcement(announcementReceiver.getAnnouncement())
                .build();
    }
}
