package com.keepitup.magjobbackend.chat.service.api;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ChatService {
    Optional<Chat> find(BigInteger id);

    Optional<Chat> findByTitle(String title);

    List<Chat> findAll();

    Page<Chat> findAll(Pageable pageable);

    Page<Chat> findAllByOrganization(Organization organization, Pageable pageable);

    void addAdmin(Chat chat, ChatMember chatMember);

    void removeAdmin(Chat chat, ChatMember chatMember);

    void create(Chat chat);

    void delete(BigInteger id);

    void update(Chat chat);
}
