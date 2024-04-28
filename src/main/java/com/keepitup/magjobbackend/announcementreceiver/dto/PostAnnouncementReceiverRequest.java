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
@Schema(description = "PostAnnouncementReceiverRequest DTO")
public class PostAnnouncementReceiverRequest {
    @Schema(description = "Member id value")
    private BigInteger member;

    @Schema(description = "Announcement id value")
    private BigInteger announcement;
}
