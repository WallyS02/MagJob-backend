package com.keepitup.magjobbackend.assignee.repository.api;

import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.entity.AssigneeId;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssigneeRepository extends JpaRepository<Assignee, AssigneeId> {
    List<Assignee> findAllByMember(Member member);
    List<Assignee> findAllByTask(Task task);
    Optional<Assignee> findByMember_IdAndTask_Id(BigInteger memberId, BigInteger taskId);
}
