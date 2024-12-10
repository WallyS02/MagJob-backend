package com.keepitup.magjobbackend.chatmessage.function;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.chatmessage.dto.PostChatMessageRequest;
import com.keepitup.magjobbackend.chatmessage.entity.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToChatMessageFunction implements Function<PostChatMessageRequest, ChatMessage> {
    @Override
    public ChatMessage apply(PostChatMessageRequest postChatMessageRequest) {
        Chat chat = Chat.builder()
                .id(postChatMessageRequest.getChat())
                .build();

        ChatMember chatMember = ChatMember.builder()
                .id(postChatMessageRequest.getChatMember())
                .chat(chat)
                .build();

        return ChatMessage.builder()
                .content(postChatMessageRequest.getContent())
                .attachment(postChatMessageRequest.getAttachment())
                .chat(chat)
                .chatMember(chatMember)
                .build();
    }
}
