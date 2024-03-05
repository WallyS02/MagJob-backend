package com.keepitup.magjobbackend.assignee.service.api;

import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.entity.AssigneeId;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.task.entity.Task;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface AssigneeService {
    List<Assignee> findAll();

    Optional<Assignee> find(AssigneeId id);

    Optional<Assignee> findByMemberAndTask(BigInteger memberId, BigInteger taskId);

    List<Assignee> findAllByMember(Member member);

    List<Assignee> findAllByTask(Task task);

    void create(Assignee assignee);

    void delete(AssigneeId id);

    void delete(BigInteger memberId, BigInteger taskId);

    void update(Assignee assignee);

    Optional<List<Task>> findAllTasksByMember(BigInteger memberId);
}
