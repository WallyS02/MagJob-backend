package com.keepitup.magjobbackend.assignee.function;

import com.keepitup.magjobbackend.assignee.dto.GetAssigneeResponse;
import com.keepitup.magjobbackend.assignee.entity.Assignee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component

public class AssigneeToResponseFunction implements Function<Assignee, GetAssigneeResponse> {
    @Override
    public GetAssigneeResponse apply(Assignee assignee) {
        return GetAssigneeResponse.builder()
                .member(GetAssigneeResponse.Member.builder()
                        .id(assignee.getMember().getId())
                        .pseudonym(assignee.getMember().getPseudonym())
                        .build())
                .task(GetAssigneeResponse.Task.builder()
                        .id(assignee.getTask().getId())
                        .title(assignee.getTask().getTitle())
                        .build())
                .build();
    }
}
