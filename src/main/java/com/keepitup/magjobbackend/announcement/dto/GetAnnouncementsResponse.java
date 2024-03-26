package com.keepitup.magjobbackend.announcement.dto;

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
@Schema(description = "GetAnnouncementsResponse DTO")
public class GetAnnouncementsResponse {
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

        @Schema(description = "Organization id value")
        private BigInteger organizationId;

        @Schema(description = "Announcement title")
        private String title;

        @Schema(description = "Announcement content")
        private String content;
    }

    @Singular
    @Schema(description = "Announcement list")
    private List<GetAnnouncementsResponse.Announcement> announcements;
}
