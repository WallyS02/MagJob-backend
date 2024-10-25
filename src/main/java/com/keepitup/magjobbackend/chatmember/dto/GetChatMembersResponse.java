package com.keepitup.magjobbackend.chatmember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
@Schema(description = "GetChatMembersResponse DTO")
public class GetChatMembersResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class ChatMember {
        @Schema(description = "Chat member id value")
        private BigInteger id;

        @Schema(description = "Chat member nickname value")
        private String nickname;
    }

    @Singular
    @Schema(description = "Chat member list")
    private List<ChatMember> chatMembers;

    @Schema(description = "Number of all objects")
    private Integer count;
}
