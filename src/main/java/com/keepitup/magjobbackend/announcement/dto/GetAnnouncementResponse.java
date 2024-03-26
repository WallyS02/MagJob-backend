package com.keepitup.magjobbackend.announcement.dto;

import com.keepitup.magjobbackend.member.dto.GetMemberResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetAnnouncementResponse DTO")
public class GetAnnouncementResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Organization {

        @Schema(description = "Organization id value")
        private BigInteger id;

        @Schema(description = "Organization name")
        private String name;

    }

    @Schema(description = "Announcement id value")
    private BigInteger id;

    @Schema(description = "Announcement title")
    private String title;

    @Schema(description = "Announcement content")
    private String content;

    @Schema(description = "Announcement date of expiration")
    private ZonedDateTime dateOfExpiration;

    @Schema(description = "Organization class value")
    private GetMemberResponse.Organization organization;
}
