package com.keepitup.magjobbackend.chatmember.function;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmember.dto.PostChatMemberRequest;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToChatMemberFunction implements Function<PostChatMemberRequest, ChatMember> {
    @Override
    public ChatMember apply(PostChatMemberRequest postChatMemberRequest) {
        return ChatMember.builder()
                .nickname(postChatMemberRequest.getNickname())
                .member(Member.builder()
                        .id(postChatMemberRequest.getMember())
                        .build())
                .chat(Chat.builder()
                        .id(postChatMemberRequest.getChat())
                        .build())
                .build();
    }
}
