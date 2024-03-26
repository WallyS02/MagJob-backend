package com.keepitup.magjobbackend.announcementreceiver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetAnnouncementReceiversResponse DTO")
public class GetAnnouncementReceiversResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class AnnouncementReceiver {

        @Schema(description = "Announcement receiver id value")
        private BigInteger id;

        @Schema(description = "Member id value")
        private BigInteger memberId;

        @Schema(description = "Announcement id value")
        private BigInteger announcementId;
    }

    @Singular
    @Schema(description = "Announcement receiver list")
    private List<GetAnnouncementReceiversResponse.AnnouncementReceiver> announcementReceivers;
}
