package com.keepitup.magjobbackend.assignee.service.api;

import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.entity.AssigneeId;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface AssigneeService {
    List<Assignee> findAll();

    Page<Assignee> findAll(Pageable pageable);

    Optional<Assignee> find(AssigneeId id);

    Optional<Assignee> findByMemberAndTask(BigInteger memberId, BigInteger taskId);

    Page<Assignee> findAllByMember(Member member, Pageable pageable);

    Page<Assignee> findAllByTask(Task task, Pageable pageable);

    void create(Assignee assignee);

    void delete(AssigneeId id);

    void delete(BigInteger memberId, BigInteger taskId);

    void update(Assignee assignee);

    Optional<Page<Task>> findAllTasksByMember(BigInteger memberId, Pageable pageable);
}
