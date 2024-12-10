package com.keepitup.magjobbackend.chat.function;

import com.keepitup.magjobbackend.chat.dto.PostChatRequest;
import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToChatFunction implements Function<PostChatRequest, Chat> {
    @Override
    public Chat apply(PostChatRequest postChatRequest) {
        return Chat.builder()
                .title(postChatRequest.getTitle())
                .organization(Organization.builder()
                        .id(postChatRequest.getOrganization())
                        .build())
                .build();
    }
}
