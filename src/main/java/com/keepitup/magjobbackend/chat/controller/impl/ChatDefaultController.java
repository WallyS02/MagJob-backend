package com.keepitup.magjobbackend.chat.controller.impl;

import com.keepitup.magjobbackend.chat.controller.api.ChatController;
import com.keepitup.magjobbackend.chat.dto.GetChatResponse;
import com.keepitup.magjobbackend.chat.dto.GetChatsResponse;
import com.keepitup.magjobbackend.chat.dto.PatchChatRequest;
import com.keepitup.magjobbackend.chat.dto.PostChatRequest;
import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chat.function.ChatToResponseFunction;
import com.keepitup.magjobbackend.chat.function.ChatsToResponseFunction;
import com.keepitup.magjobbackend.chat.function.RequestToChatFunction;
import com.keepitup.magjobbackend.chat.function.UpdateChatWithRequestFunction;
import com.keepitup.magjobbackend.chat.service.impl.ChatDefaultService;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.chatmember.service.api.ChatMemberService;
import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.api.OrganizationService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@Controller
@Log
public class ChatDefaultController implements ChatController {
    private final ChatDefaultService chatService;
    private final SecurityService securityService;
    private final OrganizationService organizationService;
    private final MemberService memberService;
    private final ChatMemberService chatMemberService;

    private final ChatToResponseFunction chatToResponseFunction;
    private final ChatsToResponseFunction chatsToResponseFunction;
    private final RequestToChatFunction requestToChatFunction;
    private final UpdateChatWithRequestFunction updateChatWithRequestFunction;

    @Autowired
    public ChatDefaultController(
            ChatDefaultService chatService,
            SecurityService securityService,
            OrganizationService organizationService,
            MemberService memberService,
            ChatMemberService chatMemberService,
            ChatToResponseFunction chatToResponseFunction,
            ChatsToResponseFunction chatsToResponseFunction,
            RequestToChatFunction requestToChatFunction,
            UpdateChatWithRequestFunction updateChatWithRequestFunction
    ) {
        this.chatService = chatService;
        this.securityService = securityService;
        this.organizationService = organizationService;
        this.memberService = memberService;
        this.chatMemberService = chatMemberService;
        this.chatToResponseFunction = chatToResponseFunction;
        this.chatsToResponseFunction = chatsToResponseFunction;
        this.requestToChatFunction = requestToChatFunction;
        this.updateChatWithRequestFunction = updateChatWithRequestFunction;
    }

    @Override
    public GetChatsResponse getChats(int page, int size) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = chatService.findAll().size();
        return chatsToResponseFunction.apply(chatService.findAll(pageRequest), count);
    }

    @Override
    public GetChatResponse getChat(BigInteger id) {
        Chat chat = chatService.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member member = securityService.getCurrentMember(chat.getOrganization());

        boolean isChatMember = chat.getChatMembers().stream()
                .anyMatch(chatMember -> chatMember.getMember().equals(member));

        if (!isChatMember) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return chatToResponseFunction.apply(chat);
    }

    @Override
    public GetChatsResponse getChatsByOrganization(int page, int size, BigInteger organizationId) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Optional<Organization> organizationOptional = organizationService.find(organizationId);

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!securityService.isOwner(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Integer count = chatService.findAllByOrganization(organization, Pageable.unpaged()).getNumberOfElements();

        return chatsToResponseFunction.apply(chatService.findAllByOrganization(organization, pageRequest), count);
    }

    @Override
    public GetChatsResponse getChatsByMember(int page, int size, BigInteger memberId) {
        Member member = memberService.find(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!securityService.getCurrentMember(member.getOrganization()).getId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        Optional<Page<Chat>> countOptional = chatMemberService.findAllChatsByMember(memberId, Pageable.unpaged());
        Integer count = countOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getNumberOfElements();

        Optional<Page<Chat>> chatsOptional = chatMemberService.findAllChatsByMember(memberId, pageRequest);

        Page<Chat> chats = chatsOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return chatsToResponseFunction.apply(chats, count);
    }

    @Override
    public GetChatResponse createChat(PostChatRequest postChatRequest) {
        Optional<Organization> organizationOptional = organizationService.find(postChatRequest.getOrganization());

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Member member = securityService.getCurrentMember(organization);

        chatService.create(requestToChatFunction.apply(postChatRequest));
        Optional<Chat> createdChat = chatService.findByTitle(postChatRequest.getTitle());

        if (createdChat.isPresent()) {
            chatMemberService.acceptInvitation(ChatMember.builder()
                    .chat(createdChat.get())
                    .nickname(member.getPseudonym())
                    .member(member)
                    .build());

            ChatMember adminChatMember = chatMemberService.findByMemberAndChat(member, createdChat.get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            chatService.addAdmin(createdChat.get(), adminChatMember);
        }

        return chatService.findByTitle(postChatRequest.getTitle())
                .map(chatToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetChatResponse updateChat(BigInteger id, PatchChatRequest patchChatRequest) {
        Chat chat = chatService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isChatAdmin(chat)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        chatService.update(updateChatWithRequestFunction.apply(chat, patchChatRequest));

        return chatService.find(id)
                .map(chatToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteChat(BigInteger id) {
        Chat chat = chatService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.isChatAdmin(chat)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        chatService.delete(id);
    }
}
