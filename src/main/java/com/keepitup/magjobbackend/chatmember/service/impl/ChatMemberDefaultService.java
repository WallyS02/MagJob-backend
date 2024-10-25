package com.keepitup.magjobbackend.chatmember.service.impl;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmember.service.api.ChatMemberService;
import com.keepitup.magjobbackend.chatmember.repository.api.ChatMemberRepository;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.repository.api.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class ChatMemberDefaultService implements ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ChatMemberDefaultService(
            ChatMemberRepository chatMemberRepository,
            MemberRepository memberRepository
    ) {
        this.chatMemberRepository = chatMemberRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Page<ChatMember> findAllByMember(Member member, Pageable pageable) {
        return chatMemberRepository.findAllByMember(member, pageable);
    }

    @Override
    public Page<ChatMember> findAllAcceptedMembers(Chat chat, Pageable pageable) {
        return chatMemberRepository.findAllByChatAndIsInvitationAccepted(chat, true, pageable);
    }

    @Override
    public Page<ChatMember> findAllPendingInvitationMembers(Chat chat, Pageable pageable) {
        return chatMemberRepository.findAllByChatAndIsInvitationAccepted(chat, false, pageable);
    }

    @Override
    public Optional<Page<Chat>> findAllChatsByMember(BigInteger memberId, Pageable pageable) {
        return memberRepository.findById(memberId)
                .map(member -> chatMemberRepository.findAllByMember(member, pageable))
                .map(chatMembers -> chatMembers.map(ChatMember::getChat));
    }

    @Override
    public Optional<ChatMember> find(BigInteger id) {
        return chatMemberRepository.findById(id);
    }

    @Override
    public Optional<ChatMember> findByMemberAndChat(Member member, Chat chat) {
        return chatMemberRepository.findByMemberAndChat(member, chat);
    }

    @Override
    public void create(ChatMember chatMember) {
        chatMember.setIsInvitationAccepted(false);
        chatMemberRepository.save(chatMember);
    }

    @Override
    public void acceptInvitation(ChatMember chatMember) {
        chatMember.setIsInvitationAccepted(true);
        chatMemberRepository.save(chatMember);
    }

    @Override
    public void delete(BigInteger id) {
        chatMemberRepository.findById(id).ifPresent(chatMemberRepository::delete);
    }

    @Override
    public void update(ChatMember chatMember) {
        chatMemberRepository.save(chatMember);
    }
}
