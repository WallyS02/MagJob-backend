package com.keepitup.magjobbackend.announcementreceiver.function;

import com.keepitup.magjobbackend.announcementreceiver.dto.GetAnnouncementReceiversResponse;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AnnouncementReceiversToResponseFunction implements BiFunction<Page<AnnouncementReceiver>, Integer, GetAnnouncementReceiversResponse> {
    @Override
    public GetAnnouncementReceiversResponse apply(Page<AnnouncementReceiver> announcementReceivers, Integer count) {
        return GetAnnouncementReceiversResponse.builder()
                .announcementReceivers(announcementReceivers.stream()
                        .map(announcementReceiver -> GetAnnouncementReceiversResponse.AnnouncementReceiver.builder()
                                .id(announcementReceiver.getId())
                                .memberId(announcementReceiver.getMember().getId())
                                .announcementId(announcementReceiver.getAnnouncement().getId())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
