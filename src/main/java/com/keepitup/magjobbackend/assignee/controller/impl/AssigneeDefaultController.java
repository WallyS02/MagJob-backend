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
import com.keepitup.magjobbackend.configuration.Constants;
import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.impl.MemberDefaultService;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.notification.service.impl.NotificationDefaultService;
import com.keepitup.magjobbackend.organization.entity.Organization;
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
    private final NotificationDefaultService notificationService;
    private final MemberDefaultService memberService;
    private final SecurityService securityService;

    public AssigneeDefaultController(
            AssigneeService service,
            RequestToAssigneeFunction requestToAssignee,
            AssigneeToResponseFunction assigneeToResponse,
            AssigneesToResponseFunction assigneesToResponse,
            TaskService taskService,
            NotificationDefaultService notificationService,
            MemberDefaultService memberService,
            SecurityService securityService
    ) {
        this.service = service;
        this.requestToAssignee = requestToAssignee;
        this.assigneeToResponse = assigneeToResponse;
        this.assigneesToResponse = assigneesToResponse;
        this.taskService = taskService;
        this.notificationService = notificationService;
        this.memberService = memberService;
        this.securityService = securityService;
    }


    @Override
    public GetAssigneeResponse getAssignee(BigInteger memberId, BigInteger taskId) {
        Organization organization = taskService.find(taskId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getOrganization();

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.findByMemberAndTask(memberId, taskId)
                .map(assigneeToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetAssigneeResponse assignMemberToTask(PostAssigneeRequest request) {
        Organization organization = taskService.find(request.getTask()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getOrganization();

        Member member = memberService.find(request.getMember()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (!securityService.hasPermission(organization, Constants.PERMISSION_NAME_CAN_MANAGE_TASKS)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }


        Optional<Assignee> assignee = service.findByMemberAndTask(request.getMember(), request.getTask());

        if (assignee.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            service.create(requestToAssignee.apply(request));
        }

        notificationService.create(Notification.builder()
                .member(member)
                .content(String.format(Constants.NOTIFICATION_ASSIGNEE_CREATION_TEMPLATE, organization.getName()))
                .build());

        return service.findByMemberAndTask(request.getMember(), request.getTask())
                .map(assigneeToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetAssigneesResponse getAssigneesByTask(int page, int size, BigInteger taskId) {
        Organization organization = taskService.find(taskId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getOrganization();

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
  
        PageRequest pageRequest = PageRequest.of(page, size);
        Optional<Task> task = taskService.find(taskId);

        Integer count = service.findAllByTask(task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), Pageable.unpaged()).getNumberOfElements();

        return assigneesToResponse.apply(
                service.findAllByTask(task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), pageRequest), count
        );
    }

    @Override
    public void deleteMemberFromTask(PostAssigneeRequest request) {
        Organization organization = taskService.find(request.getTask()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getOrganization();

        if (!securityService.hasPermission(organization, Constants.PERMISSION_NAME_CAN_MANAGE_TASKS)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        service.findByMemberAndTask(request.getMember(), request.getTask())
                .ifPresentOrElse(
                        assignee -> {
                            service.delete(request.getMember(), request.getTask());

                            notificationService.create(Notification.builder()
                                    .member(assignee.getMember())
                                    .content(String.format(Constants.NOTIFICATION_ASSIGNEE_DELETION_TEMPLATE, organization.getName()))
                                    .build());
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
