package com.keepitup.magjobbackend.assignee.repository.api;

import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.entity.AssigneeId;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AssigneeRepository extends JpaRepository<Assignee, AssigneeId> {
    Page<Assignee> findAllByMember(Member member, Pageable pageable);
    Page<Assignee> findAllByTask(Task task, Pageable pageable);
    Optional<Assignee> findByMember_IdAndTask_Id(BigInteger memberId, BigInteger taskId);
}
