package com.keepitup.magjobbackend.chatmessage.service.impl;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmessage.entity.ChatMessage;
import com.keepitup.magjobbackend.chatmessage.repository.api.ChatMessageRepository;
import com.keepitup.magjobbackend.chatmessage.service.api.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ChatMessageDefaultService implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageDefaultService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public Optional<ChatMessage> find(BigInteger id) {
        return chatMessageRepository.findById(id);
    }

    @Override
    public Page<ChatMessage> findAllByChat(Chat chat, Pageable pageable) {
        return chatMessageRepository.findAllByChat(chat, pageable);
    }

    @Override
    public ChatMessage create(ChatMessage chatMessage) {
        String firstName = chatMessage.getChatMember().getMember().getUser().getFirstname();
        String lastName = chatMessage.getChatMember().getMember().getUser().getLastname();

        chatMessage.setDateOfCreation(LocalDate.now());
        chatMessage.setFirstAndLastName(String.join(" ", firstName, lastName));
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public void update(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }
}
