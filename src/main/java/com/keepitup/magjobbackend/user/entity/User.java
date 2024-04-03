package com.keepitup.magjobbackend.user.entity;

import com.keepitup.magjobbackend.invitation.entity.Invitation;
import com.keepitup.magjobbackend.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator")
    private BigInteger id;

    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String externalId;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @Size(max = 50)
    @Column(length = 50)
    private String firstname;

    @Size(max = 50)
    @Column(length = 50)
    private String lastname;

    @Column(name = "phone_number", length = 9, unique = true)
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user")
    private List<Member> memberships;

    @OneToMany(mappedBy = "user")
    private List<Invitation> invitations;
}
