package com.keepitup.magjobbackend.assignee.controller.impl;

import com.keepitup.magjobbackend.assignee.controller.api.AssigneeController;
import com.keepitup.magjobbackend.assignee.dto.GetAssigneeResponse;
import com.keepitup.magjobbackend.assignee.dto.GetAssigneesResponse;
import com.keepitup.magjobbackend.assignee.dto.PostAssigneeRequest;
import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.function.AssigneeToResponseFunction;
import com.keepitup.magjobbackend.assignee.function.AssigneesToResponseFunction;
import com.keepitup.magjobbackend.assignee.function.RequestToAssigneeFunction;
import com.keepitup.magjobbackend.assignee.service.api.AssigneeService;
import com.keepitup.magjobbackend.task.entity.Task;
import com.keepitup.magjobbackend.task.service.api.TaskService;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@RestController
@Log
public class AssigneeDefaultController implements AssigneeController {

    private final AssigneeService service;
    private final RequestToAssigneeFunction requestToAssignee;
    private final AssigneeToResponseFunction assigneeToResponse;
    private final AssigneesToResponseFunction assigneesToResponse;
    private final TaskService taskService;

    public AssigneeDefaultController(
            AssigneeService service,
            RequestToAssigneeFunction requestToAssignee,
            AssigneeToResponseFunction assigneeToResponse,
            AssigneesToResponseFunction assigneesToResponse,
            TaskService taskService
    ) {
        this.service = service;
        this.requestToAssignee = requestToAssignee;
        this.assigneeToResponse = assigneeToResponse;
        this.assigneesToResponse = assigneesToResponse;
        this.taskService = taskService;
    }


    @Override
    public GetAssigneeResponse getAssignee(BigInteger memberId, BigInteger taskId) {
        return service.findByMemberAndTask(memberId, taskId)
                .map(assigneeToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetAssigneeResponse assignMemberToTask(PostAssigneeRequest request) {
        Optional<Assignee> assignee = service.findByMemberAndTask(request.getMember(), request.getTask());

        if (assignee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            service.create(requestToAssignee.apply(request));
        }

        return service.findByMemberAndTask(request.getMember(), request.getTask())
                .map(assigneeToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetAssigneesResponse getAssigneesByTask(int page, int size, BigInteger taskId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Optional<Task> task = taskService.find(taskId);

        Integer count = service.findAllByTask(task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), Pageable.unpaged()).getNumberOfElements();

        return assigneesToResponse.apply(
                service.findAllByTask(task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), pageRequest), count
        );
    }

    @Override
    public void deleteMemberFromTask(PostAssigneeRequest request) {
        service.findByMemberAndTask(request.getMember(), request.getTask())
                .ifPresentOrElse(
                        assignee -> service.delete(request.getMember(), request.getTask()),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
