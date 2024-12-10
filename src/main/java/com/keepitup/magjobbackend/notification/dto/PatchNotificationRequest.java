package com.keepitup.magjobbackend.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PatchNotificationRequest DTO")
public class PatchNotificationRequest {
    @Schema(description = "Notification seen value")
    private boolean seen;

    @Schema(description = "Notification sent value")
    private boolean sent;
}
