package com.keepitup.magjobbackend.chatmember.entity;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "chat_members")
public class ChatMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatMemberSequenceGenerator")
    @SequenceGenerator(name = "chatMemberSequenceGenerator")
    private BigInteger id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "is_invitation_accepted")
    private Boolean isInvitationAccepted;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

  //  @OneToMany(mappedBy = "chat_member")
   // private List<ChatMessage> chatMessages;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
