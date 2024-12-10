package com.keepitup.magjobbackend.chatmessage.function;

import com.keepitup.magjobbackend.chatmessage.dto.GetChatMessageResponse;
import com.keepitup.magjobbackend.chatmessage.entity.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ChatMessageToResponseFunction implements Function<ChatMessage, GetChatMessageResponse> {
    @Override
    public GetChatMessageResponse apply(ChatMessage chatMessage) {
        return GetChatMessageResponse.builder()
                .id(chatMessage.getId())
                .content(chatMessage.getContent())
                .attachment(chatMessage.getAttachment())
                .viewedBy(chatMessage.getViewedBy())
                .firstAndLastName(chatMessage.getFirstAndLastName())
                .dateOfCreation(chatMessage.getDateOfCreation())
                .chatMember(GetChatMessageResponse.ChatMember.builder()
                        .id(chatMessage.getChatMember().getId())
                        .nickname(chatMessage.getChatMember().getNickname())
                        .memberId(chatMessage.getChatMember().getMember().getId())
                        .build())
                .chat(GetChatMessageResponse.Chat.builder()
                        .id(chatMessage.getChat().getId())
                        .title(chatMessage.getChat().getTitle())
                        .organizationId(chatMessage.getChat().getOrganization().getId())
                        .build())
                .build();
    }
}
