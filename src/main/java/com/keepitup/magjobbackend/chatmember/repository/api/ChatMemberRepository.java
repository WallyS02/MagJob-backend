package com.keepitup.magjobbackend.chatmember.repository.api;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMember, BigInteger> {
    Page<ChatMember> findAllByMember(Member member, Pageable pageable);

    Optional<ChatMember> findByMemberAndChat(Member member, Chat chat);
    Page<ChatMember> findAllByChatAndIsInvitationAccepted(Chat chat, Boolean isInvitationAccepted, Pageable pageable);
}
