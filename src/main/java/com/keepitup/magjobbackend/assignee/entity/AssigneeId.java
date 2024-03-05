package com.keepitup.magjobbackend.assignee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Embeddable
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssigneeId implements Serializable {
    @Column(name = "task_id")
    private BigInteger taskId;

    @Column(name = "member_id")
    private BigInteger memberId;
}
