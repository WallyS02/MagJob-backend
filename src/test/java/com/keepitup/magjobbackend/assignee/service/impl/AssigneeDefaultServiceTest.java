package com.keepitup.magjobbackend.assignee.service.impl;

import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.entity.AssigneeId;
import com.keepitup.magjobbackend.assignee.repository.api.AssigneeRepository;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.repository.api.MemberRepository;
import com.keepitup.magjobbackend.task.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AssigneeDefaultServiceTest {

    private AssigneeDefaultService assigneeService;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assigneeService = new AssigneeDefaultService(assigneeRepository, memberRepository);
    }

    @Test
    void testFindAll() {
        // Arrange
        Assignee assignee1 = new Assignee();
        Assignee assignee2 = new Assignee();
        List<Assignee> expectedAssignees = Arrays.asList(assignee1, assignee2);

        when(assigneeRepository.findAll()).thenReturn(expectedAssignees);

        // Act
        List<Assignee> actualAssignees = assigneeService.findAll();

        // Assert
        assertEquals(expectedAssignees.size(), actualAssignees.size());
        assertEquals(expectedAssignees.get(0), actualAssignees.get(0));
        assertEquals(expectedAssignees.get(1), actualAssignees.get(1));
    }

    @Test
    void testFind() {
        // Arrange
        AssigneeId assigneeId = new AssigneeId();
        assigneeId.setMemberId(BigInteger.ONE);
        assigneeId.setTaskId(BigInteger.ONE);
        Assignee expectedAssignee = new Assignee();
        expectedAssignee.setId(assigneeId);

        when(assigneeRepository.findById(assigneeId)).thenReturn(Optional.of(expectedAssignee));

        // Act
        Optional<Assignee> actualAssignee = assigneeService.find(assigneeId);

        // Assert
        assertEquals(expectedAssignee, actualAssignee.orElse(null));
    }

    @Test
    void testFindByMemberAndTask() {
        // Arrange
        BigInteger memberId = BigInteger.ONE;
        BigInteger taskId = BigInteger.ONE;
        AssigneeId assigneeId = new AssigneeId();
        assigneeId.setMemberId(memberId);
        assigneeId.setTaskId(taskId);
        Assignee expectedAssignee = new Assignee();
        expectedAssignee.setId(assigneeId);

        when(assigneeRepository.findByMember_IdAndTask_Id(memberId, taskId)).thenReturn(Optional.of(expectedAssignee));

        // Act
        Optional<Assignee> actualAssignee = assigneeService.findByMemberAndTask(memberId, taskId);

        // Assert
        assertEquals(expectedAssignee, actualAssignee.orElse(null));
    }

    @Test
    void testFindAllByMember() {
        // Arrange
        Member member = new Member();
        member.setId(BigInteger.ONE);
        BigInteger taskId = BigInteger.ONE;
        AssigneeId assigneeId = new AssigneeId();
        assigneeId.setMemberId(member.getId());
        assigneeId.setTaskId(taskId);
        Assignee assignee1 = new Assignee();
        assignee1.setId(assigneeId);
        BigInteger taskId2 = BigInteger.TWO;
        AssigneeId assigneeId2 = new AssigneeId();
        assigneeId.setMemberId(member.getId());
        assigneeId.setTaskId(taskId2);
        Assignee assignee2 = new Assignee();
        assignee2.setId(assigneeId2);
        Page<Assignee> expectedAssignees = new PageImpl<>(List.of(assignee1, assignee2));
        PageRequest pageRequest = PageRequest.of(0, 2);

        when(assigneeRepository.findAllByMember(member, pageRequest)).thenReturn(expectedAssignees);

        // Act
        Page<Assignee> actualAssignees = assigneeService.findAllByMember(member, pageRequest);

        // Assert
        assertEquals(expectedAssignees.getNumberOfElements(), actualAssignees.getNumberOfElements());
    }

    @Test
    void testFindAllByTask() {
        // Arrange
        Task task = new Task();
        task.setId(BigInteger.ONE);
        BigInteger memberId = BigInteger.ONE;
        AssigneeId assigneeId = new AssigneeId();
        assigneeId.setMemberId(memberId);
        assigneeId.setTaskId(task.getId());
        Assignee assignee1 = new Assignee();
        assignee1.setId(assigneeId);
        BigInteger memberId2 = BigInteger.ONE;
        AssigneeId assigneeId2 = new AssigneeId();
        assigneeId.setMemberId(memberId2);
        assigneeId.setTaskId(task.getId());
        Assignee assignee2 = new Assignee();
        assignee2.setId(assigneeId2);
        Page<Assignee> expectedAssignees = new PageImpl<>(List.of(assignee1, assignee2));
        PageRequest pageRequest = PageRequest.of(0, 2);

        when(assigneeRepository.findAllByTask(task, pageRequest)).thenReturn(expectedAssignees);

        // Act
        Page<Assignee> actualAssignees = assigneeService.findAllByTask(task, pageRequest);

        // Assert
        assertEquals(expectedAssignees.getNumberOfElements(), actualAssignees.getNumberOfElements());
    }

    @Test
    void testCreate() {
        // Arrange
        Assignee assignee = new Assignee();

        // Act
        assigneeService.create(assignee);

        // Assert
        verify(assigneeRepository).save(assignee);
    }

    @Test
    void testDeleteByAssigneeId() {
        // Arrange
        AssigneeId assigneeId = new AssigneeId();
        assigneeId.setMemberId(BigInteger.ONE);
        assigneeId.setTaskId(BigInteger.ONE);
        Assignee assignee = new Assignee();
        assignee.setId(assigneeId);

        when(assigneeRepository.findById(assigneeId)).thenReturn(Optional.of(assignee));

        // Act
        assigneeService.delete(assigneeId);

        // Assert
        verify(assigneeRepository).delete(assignee);
    }

    @Test
    void testDeleteByMemberIdAndTaskId() {
        // Arrange
        BigInteger memberId = BigInteger.ONE;
        BigInteger taskId = BigInteger.ONE;
        AssigneeId assigneeId = new AssigneeId();
        assigneeId.setMemberId(memberId);
        assigneeId.setTaskId(taskId);
        Assignee assignee = new Assignee();
        assignee.setId(assigneeId);

        when(assigneeRepository.findByMember_IdAndTask_Id(memberId, taskId)).thenReturn(Optional.of(assignee));

        // Act
        assigneeService.delete(memberId, taskId);

        // Assert
        verify(assigneeRepository).delete(assignee);
    }

    @Test
    void testUpdate() {
        // Arrange
        Assignee assignee = new Assignee();

        // Act
        assigneeService.update(assignee);

        // Assert
        verify(assigneeRepository).save(assignee);
    }

    @Test
    void testFindAllTasksByMember() {
        // Arrange
        BigInteger memberId = BigInteger.ONE;
        Task task1 = new Task();
        task1.setId(BigInteger.ONE);
        Task task2 = new Task();
        task2.setId(BigInteger.TWO);
        Assignee assignee1 = new Assignee();
        assignee1.setTask(task1);
        Assignee assignee2 = new Assignee();
        assignee2.setTask(task2);
        Member member = new Member();
        member.setId(memberId);
        Page<Assignee> assigneeList = new PageImpl<>(List.of(assignee1, assignee2));
        PageRequest pageRequest = PageRequest.of(0, 2);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(assigneeRepository.findAllByMember(member, pageRequest)).thenReturn(assigneeList);

        // Act
        Optional<Page<Task>> tasksOptional = assigneeService.findAllTasksByMember(memberId, pageRequest);

        // Assert
        assertEquals(2, tasksOptional.orElseThrow().getNumberOfElements());
    }
}
