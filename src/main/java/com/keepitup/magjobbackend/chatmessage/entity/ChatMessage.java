package com.keepitup.magjobbackend.chatmessage.entity;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatMessageSequenceGenerator")
    @SequenceGenerator(name = "chatMessageSequenceGenerator")
    BigInteger id;

    @Column(name = "content")
    String content;

    @Column(name = "date_of_creation")
    LocalDate dateOfCreation;

    @ElementCollection
    List<String> viewedBy;

    @Lob
    byte[] attachment;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    Chat chat;

    @ManyToOne
    @JoinColumn(name = "chat_member_id")
    ChatMember chatMember;

    @Column(name = "first_and_last_name")
    String firstAndLastName;
}
