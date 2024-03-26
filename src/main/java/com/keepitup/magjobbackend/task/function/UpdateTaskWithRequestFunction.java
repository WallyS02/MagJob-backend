package com.keepitup.magjobbackend.task.function;

import com.keepitup.magjobbackend.task.dto.PatchTaskRequest;
import com.keepitup.magjobbackend.task.entity.Task;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateTaskWithRequestFunction implements BiFunction<Task, PatchTaskRequest, Task> {
    @Override
    public Task apply(Task task, PatchTaskRequest request) {
        return Task.builder()
                .id(task.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .isDone(task.getIsDone())
                .dateOfCreation(task.getDateOfCreation())
                .dateOfCompletion(task.getDateOfCompletion())
                .deadLine(request.getDeadLine())
                .isImportant(request.getIsImportant())
                .organization(task.getOrganization())
                .build();
    }
}
