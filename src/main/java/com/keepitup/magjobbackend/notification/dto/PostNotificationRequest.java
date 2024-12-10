package com.keepitup.magjobbackend.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PostNotificationRequest DTO")
public class PostNotificationRequest {
    @Schema(description = "Notification content")
    private String content;

    @Schema(description = "Organization id value")
    private BigInteger organization;

    @Schema(description = "Member id value")
    private BigInteger member;

    @Schema(description = "User id value")
    private UUID user;
}
