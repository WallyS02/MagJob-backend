package com.keepitup.magjobbackend.chatmessage.repository.api;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmessage.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, BigInteger> {
    Page<ChatMessage> findAllByChat(Chat chat, Pageable pageable);
}
