package com.keepitup.magjobbackend.task.controller.impl;

import com.keepitup.magjobbackend.assignee.service.api.AssigneeService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.api.OrganizationService;
import com.keepitup.magjobbackend.task.controller.api.TaskController;
import com.keepitup.magjobbackend.task.dto.GetTaskResponse;
import com.keepitup.magjobbackend.task.dto.GetTasksResponse;
import com.keepitup.magjobbackend.task.dto.PatchTaskRequest;
import com.keepitup.magjobbackend.task.dto.PostTaskRequest;
import com.keepitup.magjobbackend.task.entity.Task;
import com.keepitup.magjobbackend.task.function.RequestToTaskFunction;
import com.keepitup.magjobbackend.task.function.TaskToResponseFunction;
import com.keepitup.magjobbackend.task.function.TasksToResponseFunction;
import com.keepitup.magjobbackend.task.function.UpdateTaskWithRequestFunction;
import com.keepitup.magjobbackend.task.service.api.TaskService;
import com.keepitup.magjobbackend.user.service.api.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@Log
public class TaskDefaultController implements TaskController {

    private final TaskService service;
    private final RequestToTaskFunction requestToTask;
    private final UpdateTaskWithRequestFunction updateTaskWithRequest;
    private final TasksToResponseFunction tasksToResponse;
    private final TaskToResponseFunction taskToResponse;
    private final OrganizationService organizationService;
    private final AssigneeService assigneeService;

    @Autowired
    public TaskDefaultController(
            TaskService service,
            RequestToTaskFunction requestToTask,
            UpdateTaskWithRequestFunction updateTaskWithRequest,
            TasksToResponseFunction tasksToResponse,
            TaskToResponseFunction taskToResponse,
            OrganizationService organizationService,
            UserService userService,
            AssigneeService assigneeService
    ) {
        this.service = service;
        this.requestToTask = requestToTask;
        this.updateTaskWithRequest = updateTaskWithRequest;
        this.tasksToResponse = tasksToResponse;
        this.taskToResponse = taskToResponse;
        this.organizationService = organizationService;
        this.assigneeService = assigneeService;
    }

    @Override
    public GetTasksResponse getTasks() {
        return tasksToResponse.apply(service.findAll());
    }

    @Override
    public GetTaskResponse getTask(BigInteger id) {
        return service.find(id)
                .map(taskToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetTasksResponse getTasksByOrganization(BigInteger id) {
        Organization organization = organizationService.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return tasksToResponse.apply(service.findAllByOrganization(organization));
    }

    @Override
    public GetTasksResponse getTasksByMember(BigInteger id) {
        Optional<List<Task>> tasksOptional = assigneeService.findAllTasksByMember(id);

        List<Task> tasks = tasksOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return tasksToResponse.apply(tasks);
    }

    @Override
    public GetTaskResponse createTask(PostTaskRequest postTaskRequest) {
        Optional<Task> task = service.findByTitle(postTaskRequest.getTitle());

        if (task.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            service.create(requestToTask.apply(postTaskRequest));
        }
        return service.findByTitle(postTaskRequest.getTitle())
                .map(taskToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetTaskResponse updateTask(BigInteger id, PatchTaskRequest patchTaskRequest) {
        service.find(id)
                .ifPresentOrElse(
                        task -> service.update(updateTaskWithRequest.apply(task, patchTaskRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );

        return taskToResponse.apply(service.find(id).get());
    }

    @Override
    public void completeTask(BigInteger id) {
        service.find(id)
                .ifPresentOrElse(
                        service::completeTask,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public void deleteTask(BigInteger id) {
        service.find(id)
                .ifPresentOrElse(
                        task -> service.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
