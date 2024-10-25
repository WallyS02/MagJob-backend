package com.keepitup.magjobbackend.chatmember.service.api;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Optional;

public interface ChatMemberService {
    Page<ChatMember> findAllByMember(Member member, Pageable pageable);

    Page<ChatMember> findAllAcceptedMembers(Chat chat, Pageable pageable);

    Page<ChatMember> findAllPendingInvitationMembers(Chat chat, Pageable pageable);

    Optional<Page<Chat>> findAllChatsByMember(BigInteger memberId, Pageable pageable);

    Optional<ChatMember> find(BigInteger id);

    Optional<ChatMember> findByMemberAndChat(Member member, Chat chat);

    void create(ChatMember chatMember);

    void acceptInvitation(ChatMember chatMember);

    void delete(BigInteger id);

    void update(ChatMember chatMember);
}
