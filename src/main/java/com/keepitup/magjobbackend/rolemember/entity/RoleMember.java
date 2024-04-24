package com.keepitup.magjobbackend.rolemember.entity;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
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
@Table(name = "role_members")
public class RoleMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleMemberSequenceGenerator")
    @SequenceGenerator(name = "roleMemberSequenceGenerator")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
