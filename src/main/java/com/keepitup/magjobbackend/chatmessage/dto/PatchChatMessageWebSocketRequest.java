package com.keepitup.magjobbackend.chatmessage.dto;

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
@Schema(description = "PatchChatMessageWebSocketRequest DTO")
public class PatchChatMessageWebSocketRequest {
    @Schema(description = "chat message id value")
    private BigInteger chatMessageId;

    @Schema(description = "chat message viewed by value")
    private String viewedBy;
}
