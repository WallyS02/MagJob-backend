package com.keepitup.magjobbackend.assignee.function;

import com.keepitup.magjobbackend.assignee.dto.GetAssigneesResponse;
import com.keepitup.magjobbackend.assignee.entity.Assignee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class AssigneesToResponseFunction implements Function<List<Assignee>, GetAssigneesResponse> {
    @Override
    public GetAssigneesResponse apply(List<Assignee> entities) {
        return GetAssigneesResponse.builder()
                .assignees(entities.stream()
                        .map(assignee -> GetAssigneesResponse.Assignee.builder()
                                .memberId(assignee.getMember().getId())
                                .taskId(assignee.getTask().getId())
                                .build())
                        .toList())
                .build();
    }
}
