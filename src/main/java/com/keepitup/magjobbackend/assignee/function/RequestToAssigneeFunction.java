package com.keepitup.magjobbackend.assignee.function;

import com.keepitup.magjobbackend.assignee.dto.PostAssigneeRequest;
import com.keepitup.magjobbackend.assignee.entity.Assignee;
import com.keepitup.magjobbackend.assignee.entity.AssigneeId;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToAssigneeFunction implements Function<PostAssigneeRequest, Assignee> {

    @Override
    public Assignee apply(PostAssigneeRequest request) {
        return Assignee.builder()
                .id(AssigneeId.builder()
                        .taskId(request.getTask())
                        .memberId(request.getMember())
                        .build())
                .member(Member.builder()
                        .id(request.getMember())
                        .build())
                .task(Task.builder()
                        .id(request.getTask())
                        .build())
                .build();
    }
}
