package com.keepitup.magjobbackend.announcement.dto;

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
@Schema(description = "PostAnnouncementRequest DTO")
public class PostAnnouncementRequest {
    @Schema(description = "Announcement title value")
    private String title;

    @Schema(description = "Announcement content value")
    private String content;

    @Schema(description = "Announcement date of expiration")
    private ZonedDateTime dateOfExpiration;

    @Schema(description = "Organization id value")
    private BigInteger organization;
}
