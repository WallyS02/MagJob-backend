package com.keepitup.magjobbackend.announcementreceiver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetAnnouncementReceiverResponse DTO")
public class GetAnnouncementReceiverResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member {

        @Schema(description = "Member id value")
        private BigInteger id;

        @Schema(description = "Member pseudonym")
        private String pseudonym;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Announcement {

        @Schema(description = "Announcement id value")
        private BigInteger id;

        @Schema(description = "Announcement title")
        private String title;

    }

    @Schema(description = "Announcement receiver id value")
    private BigInteger id;

    @Schema(description = "Member class value")
    private GetAnnouncementReceiverResponse.Member member;

    @Schema(description = "Announcement class value")
    private GetAnnouncementReceiverResponse.Announcement announcement;
}
