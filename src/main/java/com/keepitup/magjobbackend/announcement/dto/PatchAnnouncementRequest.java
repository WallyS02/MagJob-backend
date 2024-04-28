package com.keepitup.magjobbackend.announcement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PatchAnnouncementRequest DTO")
public class PatchAnnouncementRequest {
    @Schema(description = "Announcement title")
    private String title;

    @Schema(description = "Announcement title")
    private String content;

    @Schema(description = "Announcement date of expiration")
    private ZonedDateTime dateOfExpiration;
}
