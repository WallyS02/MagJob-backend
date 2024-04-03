package com.keepitup.magjobbackend.role.entity;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSequenceGenerator")
    @SequenceGenerator(name = "roleSequenceGenerator")
    private BigInteger id;

    @Column(name = "external_id", length = 254, unique = true)
    private String externalId;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "role")
    private List<RoleMember> roleMembers;
}
