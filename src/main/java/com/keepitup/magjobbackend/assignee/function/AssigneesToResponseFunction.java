package com.keepitup.magjobbackend.assignee.function;

import com.keepitup.magjobbackend.assignee.dto.GetAssigneesResponse;
import com.keepitup.magjobbackend.assignee.entity.Assignee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class AssigneesToResponseFunction implements BiFunction<Page<Assignee>, Integer, GetAssigneesResponse> {
    @Override
    public GetAssigneesResponse apply(Page<Assignee> entities, Integer count) {
        return GetAssigneesResponse.builder()
                .assignees(entities.stream()
                        .map(assignee -> GetAssigneesResponse.Assignee.builder()
                                .memberId(assignee.getMember().getId())
                                .taskId(assignee.getTask().getId())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
