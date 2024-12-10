package com.keepitup.magjobbackend.chat.dto;

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
@Schema(description = "PostChatRequest DTO")
public class PostChatRequest {
    @Schema(description = "Chat title")
    private String title;

    @Schema(description = "Organization id value")
    private BigInteger organization;
}
