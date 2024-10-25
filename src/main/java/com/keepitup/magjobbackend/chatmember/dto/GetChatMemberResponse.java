package com.keepitup.magjobbackend.chatmember.dto;

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
@Schema(description = "GetChatMemberResponse DTO")
public class GetChatMemberResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member {

        @Schema(description = "Member id value")
        private BigInteger id;

        @Schema(description = "Member pseudonym")
        private String pseudonym;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Chat {
        @Schema(description = "Chat id value")
        private BigInteger id;

        @Schema(description = "Chat title")
        private String title;
    }

    @Schema(description = "Chat member id value")
    private BigInteger id;

    @Schema(description = "Member class value")
    private Member member;

    @Schema(description = "Chat class value")
    private Chat chat;

    @Schema(description = "Chat member nickname value")
    private String nickname;

    @Schema(description = "Chat member is invitation accepted value")
    private Boolean isInvitationAccepted;
}
