package com.keepitup.magjobbackend.chat.service.impl;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chat.repository.api.ChatRepository;
import com.keepitup.magjobbackend.chat.service.api.ChatService;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatDefaultService implements ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatDefaultService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Optional<Chat> find(BigInteger id) {
        return chatRepository.findById(id);
    }

    @Override
    public Optional<Chat> findByTitle(String title) {
        return chatRepository.findByTitle(title);
    }

    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public Page<Chat> findAll(Pageable pageable) {
        return chatRepository.findAll(pageable);
    }

    @Override
    public Page<Chat> findAllByOrganization(Organization organization, Pageable pageable) {
        return chatRepository.findAllByOrganization(organization, pageable);
    }

    @Override
    public void addAdmin(Chat chat, ChatMember chatMember) {
        List<ChatMember> chatAdministrators = chat.getChatAdministrators();
        if (chatAdministrators == null || chatAdministrators.isEmpty()) {
            chatAdministrators = new ArrayList<>();
            chatAdministrators.add(chatMember);
        } else {
            chatAdministrators.add(chatMember);
        }
        chat.setChatAdministrators(chatAdministrators);
        chatRepository.save(chat);
    }

    @Override
    public void removeAdmin(Chat chat, ChatMember chatMember) {
        List<ChatMember> chatAdministrators = chat.getChatAdministrators();
        chatAdministrators.remove(chatMember);
        if (chatAdministrators.isEmpty()) {
            chat.setChatAdministrators(null);
            return;
        }
        chat.setChatAdministrators(chatAdministrators);
    }


    @Override
    public void create(Chat chat) {
        chat.setDateOfCreation(LocalDate.now());
        chatRepository.save(chat);
    }

    @Override
    public void delete(BigInteger id) {
        chatRepository.findById(id).ifPresent(chatRepository::delete);
    }

    @Override
    public void update(Chat chat) {
        chatRepository.save(chat);
    }
}
