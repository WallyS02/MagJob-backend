package com.keepitup.magjobbackend.assignee.service.impl;

import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.entity.AssigneeId;
import com.keepitup.magjobbackend.assignee.repository.api.AssigneeRepository;
import com.keepitup.magjobbackend.assignee.service.api.AssigneeService;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.repository.api.MemberRepository;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssigneeDefaultService implements AssigneeService {
    private final AssigneeRepository assigneeRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public AssigneeDefaultService(
            AssigneeRepository assigneeRepository,
            MemberRepository memberRepository
    ) {
        this.assigneeRepository = assigneeRepository;
        this.memberRepository = memberRepository;
    }


    @Override
    public List<Assignee> findAll() {
        return assigneeRepository.findAll();
    }

    @Override
    public Optional<Assignee> find(AssigneeId id) {
        return assigneeRepository.findById(id);
    }

    @Override
    public Optional<Assignee> findByMemberAndTask(BigInteger memberId, BigInteger taskId) {
        return assigneeRepository.findByMember_IdAndTask_Id(memberId, taskId);
    }

    @Override
    public List<Assignee> findAllByMember(Member member) {
        return assigneeRepository.findAllByMember(member);
    }

    @Override
    public List<Assignee> findAllByTask(Task task) {
        return assigneeRepository.findAllByTask(task);
    }

    @Override
    public void create(Assignee assignee) {
        assigneeRepository.save(assignee);
    }

    @Override
    public void delete(AssigneeId id) {
        assigneeRepository.findById(id).ifPresent(assigneeRepository::delete);
    }

    @Override
    public void delete(BigInteger memberId, BigInteger taskId) {
        assigneeRepository.findByMember_IdAndTask_Id(memberId, taskId).ifPresent(assigneeRepository::delete);
    }

    @Override
    public void update(Assignee assignee) {
        assigneeRepository.save(assignee);
    }

    @Override
    public Optional<List<Task>> findAllTasksByMember(BigInteger memberId) {
        return memberRepository.findById(memberId)
                .map(assigneeRepository::findAllByMember)
                .map(assignees -> assignees.stream()
                        .map(Assignee::getTask)
                        .collect(Collectors.toList()));
    }
}
