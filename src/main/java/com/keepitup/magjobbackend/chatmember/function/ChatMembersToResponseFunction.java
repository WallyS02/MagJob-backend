package com.keepitup.magjobbackend.chatmember.function;

import com.keepitup.magjobbackend.chatmember.dto.GetChatMembersResponse;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class ChatMembersToResponseFunction implements BiFunction<Page<ChatMember>, Integer, GetChatMembersResponse> {
    @Override
    public GetChatMembersResponse apply(Page<ChatMember> chatMembers, Integer count) {
        return GetChatMembersResponse.builder()
                .chatMembers(chatMembers.stream()
                        .map(chatMember -> GetChatMembersResponse.ChatMember.builder()
                                .id(chatMember.getId())
                                .nickname(chatMember.getNickname())
                                .memberId(chatMember.getMember().getId())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
