package com.keepitup.magjobbackend.chat.entity;

import com.keepitup.magjobbackend.chatmember.entity.ChatMember;
import com.keepitup.magjobbackend.organization.entity.Organization;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatSequenceGenerator")
    @SequenceGenerator(name = "chatSequenceGenerator")
    private BigInteger id;

    @Column(name = "date_of_creation")
    private LocalDate dateOfCreation;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

  //  @OneToMany(mappedBy = "chat")
 //   private List<ChatMessage> chatMessages;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<ChatMember> chatMembers;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<ChatMember> chatAdministrators;
}
