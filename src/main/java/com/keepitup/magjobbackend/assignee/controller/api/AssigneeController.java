package com.keepitup.magjobbackend.assignee.controller.api;

import com.keepitup.magjobbackend.assignee.dto.GetAssigneeResponse;
import com.keepitup.magjobbackend.assignee.dto.GetAssigneesResponse;
import com.keepitup.magjobbackend.assignee.dto.PostAssigneeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

public interface AssigneeController {

    @GetMapping("/api/assignees/{memberId}/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAssigneeResponse getAssignee(
            @PathVariable("memberId")
            BigInteger memberId,
            @PathVariable("taskId")
            BigInteger taskId
    );

    @PostMapping("/api/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetAssigneeResponse assignMemberToTask(
            @RequestBody
            PostAssigneeRequest request
    );

    @GetMapping("/api/tasks/{taskId}/assignees")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAssigneesResponse getAssigneesByTask(
        @PathVariable("taskId")
        BigInteger taskId
    );

    @DeleteMapping("/api/assignees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMemberFromTask(
            @RequestBody
            PostAssigneeRequest request
    );

    
}
