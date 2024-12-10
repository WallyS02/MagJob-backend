package com.keepitup.magjobbackend.chatmessage.service.api;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmessage.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Optional;

public interface ChatMessageService {
    Optional<ChatMessage> find(BigInteger id);
    Page<ChatMessage> findAllByChat(Chat chat, Pageable pageable);
    ChatMessage create(ChatMessage chatMessage);
    void update(ChatMessage chatMessage);
}
