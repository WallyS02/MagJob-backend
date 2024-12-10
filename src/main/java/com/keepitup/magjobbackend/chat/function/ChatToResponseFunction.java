package com.keepitup.magjobbackend.chat.function;

import com.keepitup.magjobbackend.chat.dto.GetChatResponse;
import com.keepitup.magjobbackend.chat.entity.Chat;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ChatToResponseFunction implements Function<Chat, GetChatResponse> {
    @Override
    public GetChatResponse apply(Chat chat) {
        return GetChatResponse.builder()
                .id(chat.getId())
                .title(chat.getTitle())
                .dateOfCreation(chat.getDateOfCreation())
                .organization(GetChatResponse.Organization.builder()
                        .id(chat.getOrganization().getId())
                        .name(chat.getOrganization().getName())
                        .build())
                .build();
    }
}
