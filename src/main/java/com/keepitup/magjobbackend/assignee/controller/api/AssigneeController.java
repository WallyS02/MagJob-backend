package com.keepitup.magjobbackend.assignee.controller.api;

import com.keepitup.magjobbackend.assignee.dto.GetAssigneeResponse;
import com.keepitup.magjobbackend.assignee.dto.GetAssigneesResponse;
import com.keepitup.magjobbackend.assignee.dto.PostAssigneeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name="Assignee Controller")
public interface AssigneeController {

    @Operation(summary = "Get Assignee of given Member id and Task id")
    @GetMapping("/api/assignees/{memberId}/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAssigneeResponse getAssignee(
            @Parameter(
                    name = "id",
                    description = "Member id value",
                    required = true
            )
            @PathVariable("memberId")
            BigInteger memberId,
            @Parameter(
                    name = "id",
                    description = "Task id value",
                    required = true
            )
            @PathVariable("taskId")
            BigInteger taskId
    );

    @Operation(summary = "Create Assignee")
    @PostMapping("/api/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetAssigneeResponse assignMemberToTask(
            @Parameter(
                    name = "PostAssigneeRequest",
                    description = "PostAssigneeRequest DTO",
                    schema = @Schema(implementation = PostAssigneeRequest.class),
                    required = true
            )
            @RequestBody
            PostAssigneeRequest request
    );

    @Operation(summary = "Get Assignee of given Task id")
    @GetMapping("/api/tasks/{taskId}/assignees")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAssigneesResponse getAssigneesByTask(
        @Parameter(
                name = "id",
                description = "Task id value",
                required = true
        )
        @PathVariable("taskId")
        BigInteger taskId
    );

    @Operation(summary = "Delete Assignee")
    @DeleteMapping("/api/assignees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMemberFromTask(
            @Parameter(
                    name = "PostAssigneeRequest",
                    description = "PostAssigneeRequest DTO",
                    schema = @Schema(implementation = PostAssigneeRequest.class),
                    required = true
            )
            @RequestBody
            PostAssigneeRequest request
    );

}
