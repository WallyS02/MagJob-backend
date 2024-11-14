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
@Schema(description = "PostChatMessageRequest DTO")
public class PostChatMessageRequest {
    @Schema(description = "ChatMessage content")
    private String content;

    @Schema(description = "ChatMessage attachment")
    private byte[] attachment;

    @Schema(description = "ChatMessage chatMember id value")
    private BigInteger chatMember;

    @Schema(description = "ChatMessage chat id value")
    private BigInteger chat;
}
