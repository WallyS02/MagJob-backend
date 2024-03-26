package com.keepitup.magjobbackend.task.function;

import com.keepitup.magjobbackend.task.dto.GetTasksResponse;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class TasksToResponseFunction implements Function<List<Task>, GetTasksResponse> {

    @Override
    public GetTasksResponse apply(List<Task> entities) {
        return GetTasksResponse.builder()
                .tasks(entities.stream()
                        .map(task -> GetTasksResponse.Task.builder()
                                .id(task.getId())
                                .title(task.getTitle())
                                .description(task.getDescription())
                                .dateOfCompletion(task.getDateOfCompletion())
                                .dateOfCreation(task.getDateOfCreation())
                                .deadLine(task.getDeadLine())
                                .isImportant(task.getIsImportant())
                                .isDone(task.getIsDone())
                                .build())
                        .toList())
                .build();
    }
}
