package com.keepitup.magjobbackend.announcementreceiver.entity;

import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "announcement_receivers")
public class AnnouncementReceiver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcementReceiversSequenceGenerator")
    @SequenceGenerator(name = "announcementReceiversSequenceGenerator")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
