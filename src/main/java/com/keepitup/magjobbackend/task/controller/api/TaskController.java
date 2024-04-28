package com.keepitup.magjobbackend.task.controller.api;

import com.keepitup.magjobbackend.task.dto.GetTaskResponse;
import com.keepitup.magjobbackend.task.dto.GetTasksResponse;
import com.keepitup.magjobbackend.task.dto.PatchTaskRequest;
import com.keepitup.magjobbackend.task.dto.PostTaskRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

public interface TaskController {

    @GetMapping("api/tasks")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetTasksResponse getTasks();

    @GetMapping("api/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetTaskResponse getTask(
            @PathVariable("id")
            BigInteger id
    );


    @GetMapping("api/tasks/organizations/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetTasksResponse getTasksByOrganization(
            @PathVariable("organizationId")
            BigInteger id
    );

    @GetMapping("api/tasks/members/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetTasksResponse getTasksByMember(
            @PathVariable("memberId")
            BigInteger id
    );

    @PostMapping("api/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetTaskResponse createTask(
            @RequestBody
            PostTaskRequest postTaskRequest
    );

    @PatchMapping("api/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetTaskResponse updateTask(
            @PathVariable
            BigInteger id,
            @RequestBody
            PatchTaskRequest patchTaskRequest
    );

    @PostMapping("api/tasks/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    void completeTask(
        @PathVariable
        BigInteger id
    );

    @DeleteMapping("api/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTask(
            @PathVariable
            BigInteger id
    );

}
