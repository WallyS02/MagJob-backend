package com.keepitup.magjobbackend.task.service.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.task.entity.Task;
import com.keepitup.magjobbackend.task.repository.api.TaskRepository;
import com.keepitup.magjobbackend.task.service.api.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskDefaultService implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskDefaultService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> find(BigInteger id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> findByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> findAllByOrganization(Organization organization) {
        return taskRepository.findAllByOrganization(organization);
    }

    @Override
    public List<Task> findAllByDateOfCreation(ZonedDateTime dateOfCreation) {
        return taskRepository.findAllByDateOfCreation(dateOfCreation);
    }

    @Override
    public List<Task> findAllByDateOfCompletion(ZonedDateTime dateOfCompletion) {
        return taskRepository.findAllByDateOfCompletion(dateOfCompletion);
    }

    @Override
    public List<Task> findAllByIsDone(Boolean isDone) {
        return taskRepository.findAllByIsDone(isDone);
    }

    @Override
    public List<Task> findAllByDescription(String description) {
        return taskRepository.findAllByDescription(description);
    }

    @Override
    public List<Task> findAllByIsImportant(Boolean isImportant) {
        return taskRepository.findAllByIsImportant(isImportant);
    }

    @Override
    public List<Task> findAllByDeadLine(ZonedDateTime deadLine) {
        return taskRepository.findAllByDeadLine(deadLine);
    }

    @Override
    public void create(Task task) {
        task.setDateOfCreation(ZonedDateTime.now());
        task.setIsDone(false);
        taskRepository.save(task);
    }

    @Override
    public void delete(BigInteger id) {
        taskRepository.findById(id).ifPresent(taskRepository::delete);
    }

    @Override
    public void update(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void completeTask(Task task) {
        task.setDateOfCompletion(ZonedDateTime.now());
        task.setIsDone(true);
        taskRepository.save(task);
    }
}
