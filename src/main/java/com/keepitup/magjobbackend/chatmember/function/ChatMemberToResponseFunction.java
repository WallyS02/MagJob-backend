package com.keepitup.magjobbackend.chatmember.function;

import com.keepitup.magjobbackend.chatmember.dto.GetChatMemberResponse;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ChatMemberToResponseFunction implements Function<ChatMember, GetChatMemberResponse> {
    @Override
    public GetChatMemberResponse apply(ChatMember chatMember) {
        return GetChatMemberResponse.builder()
                .id(chatMember.getId())
                .nickname(chatMember.getNickname())
                .isInvitationAccepted(chatMember.getIsInvitationAccepted())
                .member(GetChatMemberResponse.Member.builder()
                        .id(chatMember.getMember().getId())
                        .pseudonym(chatMember.getMember().getPseudonym())
                        .build())
                .chat(GetChatMemberResponse.Chat.builder()
                        .id(chatMember.getChat().getId())
                        .title(chatMember.getChat().getTitle())
                        .build())
                .build();
    }
}
