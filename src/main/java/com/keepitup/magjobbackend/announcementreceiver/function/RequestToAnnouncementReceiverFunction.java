package com.keepitup.magjobbackend.announcementreceiver.function;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcementreceiver.dto.PostAnnouncementReceiverRequest;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToAnnouncementReceiverFunction implements Function<PostAnnouncementReceiverRequest, AnnouncementReceiver> {
    @Override
    public AnnouncementReceiver apply(PostAnnouncementReceiverRequest request) {
        return AnnouncementReceiver.builder()
                .member(Member.builder()
                        .id(request.getMember())
                        .build())
                .announcement(Announcement.builder()
                        .id(request.getAnnouncement())
                        .build())
                .build();
    }
}
