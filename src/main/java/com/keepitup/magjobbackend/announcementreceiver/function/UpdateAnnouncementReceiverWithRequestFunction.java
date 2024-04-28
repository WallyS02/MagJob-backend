package com.keepitup.magjobbackend.announcementreceiver.function;

import com.keepitup.magjobbackend.announcementreceiver.dto.PatchAnnouncementReceiverRequest;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateAnnouncementReceiverWithRequestFunction implements BiFunction<AnnouncementReceiver, PatchAnnouncementReceiverRequest, AnnouncementReceiver> {
    @Override
    public AnnouncementReceiver apply(AnnouncementReceiver announcementReceiver, PatchAnnouncementReceiverRequest request) {
        return AnnouncementReceiver.builder()
                .id(announcementReceiver.getId())
                .member(announcementReceiver.getMember())
                .announcement(announcementReceiver.getAnnouncement())
                .build();
    }
}
