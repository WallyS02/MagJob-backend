package com.keepitup.magjobbackend.announcementreceiver.function;

import com.keepitup.magjobbackend.announcementreceiver.dto.GetAnnouncementReceiverResponse;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnnouncementReceiverToResponseFunction implements Function<AnnouncementReceiver, GetAnnouncementReceiverResponse> {
    @Override
    public GetAnnouncementReceiverResponse apply(AnnouncementReceiver announcementReceiver) {
        return GetAnnouncementReceiverResponse.builder()
                .id(announcementReceiver.getId())
                .member(GetAnnouncementReceiverResponse.Member.builder()
                        .id(announcementReceiver.getMember().getId())
                        .pseudonym(announcementReceiver.getMember().getPseudonym())
                        .build())
                .announcement(GetAnnouncementReceiverResponse.Announcement.builder()
                        .id(announcementReceiver.getAnnouncement().getId())
                        .title(announcementReceiver.getAnnouncement().getTitle())
                        .build())
                .build();
    }
}
