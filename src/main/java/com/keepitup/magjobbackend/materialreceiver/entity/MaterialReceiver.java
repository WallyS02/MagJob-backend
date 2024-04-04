package com.keepitup.magjobbackend.materialreceiver.entity;

import com.keepitup.magjobbackend.material.entity.Material;
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
@Table(name = "material_receivers")
public class MaterialReceiver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialReceiverSequenceGenerator")
    @SequenceGenerator(name = "materialReceiverSequenceGenerator")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
