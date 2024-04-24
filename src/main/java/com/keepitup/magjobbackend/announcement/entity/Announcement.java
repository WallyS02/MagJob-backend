package com.keepitup.magjobbackend.announcement.entity;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.announcementreceiver.entity.AnnouncementReceiver;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcementSequenceGenerator")
    @SequenceGenerator(name = "announcementSequenceGenerator")
    private BigInteger id;

    @NotNull
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "date_of_expiration")
    private ZonedDateTime dateOfExpiration;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "announcement")
    private List<AnnouncementReceiver> announcementReceivers;
}
