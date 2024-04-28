package com.keepitup.magjobbackend.assignee.entity;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.task.entity.Task;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "assignees")
public class Assignee {
    @EmbeddedId
    private AssigneeId id;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;
}
