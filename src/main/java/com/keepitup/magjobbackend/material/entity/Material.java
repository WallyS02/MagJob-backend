package com.keepitup.magjobbackend.material.entity;

import com.keepitup.magjobbackend.organization.entity.Organization;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialSequenceGenerator")
    @SequenceGenerator(name = "materialSequenceGenerator")
    private BigInteger id;

    @NotNull
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "date_of_creation", nullable = false)
    private ZonedDateTime dateOfCreation;

    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
