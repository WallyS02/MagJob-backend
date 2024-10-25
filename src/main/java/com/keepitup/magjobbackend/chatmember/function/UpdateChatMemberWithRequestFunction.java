package com.keepitup.magjobbackend.chatmember.function;

import com.keepitup.magjobbackend.chatmember.dto.PatchChatMemberRequest;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateChatMemberWithRequestFunction implements BiFunction<ChatMember, PatchChatMemberRequest, ChatMember> {
    @Override
    public ChatMember apply(ChatMember chatMember, PatchChatMemberRequest patchChatMemberRequest) {
        return ChatMember.builder()
                .id(chatMember.getId())
                .nickname(patchChatMemberRequest.getNickname())
                .isInvitationAccepted(chatMember.getIsInvitationAccepted())
                .member(chatMember.getMember())
                .chat(chatMember.getChat())
                .build();
    }
}
