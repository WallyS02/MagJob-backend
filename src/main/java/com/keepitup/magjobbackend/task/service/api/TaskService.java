package com.keepitup.magjobbackend.task.service.api;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.task.entity.Task;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();

    Optional<Task> find(BigInteger id);

    Optional<Task> findByTitle(String title);

    List<Task> findAllByOrganization(Organization organization);

    List<Task> findAllByDateOfCreation(ZonedDateTime dateOfCreation);

    List<Task> findAllByDateOfCompletion(ZonedDateTime dateOfCompletion);

    List<Task> findAllByIsDone(Boolean isDone);

    List<Task> findAllByDescription(String description);

    List<Task> findAllByIsImportant(Boolean isImportant);

    List<Task> findAllByDeadLine(ZonedDateTime deadLine);

    void create(Task task);

    void delete(BigInteger id);

    void update(Task task);

    void completeTask(Task task);
}
