package com.keepitup.magjobbackend.announcementreceiver.function;

import com.keepitup.magjobbackend.announcementreceiver.dto.GetAnnouncementReceiversResponse;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class AnnouncementReceiversToResponseFunction implements Function<List<AnnouncementReceiver>, GetAnnouncementReceiversResponse> {
    @Override
    public GetAnnouncementReceiversResponse apply(List<AnnouncementReceiver> announcementReceivers) {
        return GetAnnouncementReceiversResponse.builder()
                .announcementReceivers(announcementReceivers.stream()
                        .map(announcementReceiver -> GetAnnouncementReceiversResponse.AnnouncementReceiver.builder()
                                .id(announcementReceiver.getId())
                                .memberId(announcementReceiver.getMember().getId())
                                .announcementId(announcementReceiver.getAnnouncement().getId())
                                .build())
                        .toList())
                .build();
    }
}
