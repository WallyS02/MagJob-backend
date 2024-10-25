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
@Schema(description = "AcceptInvitationToChatRequest DTO")
public class AcceptInvitationToChatRequest {
    @Schema(description = "Member id value")
    private BigInteger member;

    @Schema(description = "Chat id value")
    private BigInteger chat;
}
