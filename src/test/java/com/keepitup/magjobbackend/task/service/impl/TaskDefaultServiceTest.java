package com.keepitup.magjobbackend.task.service.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.task.entity.Task;
import com.keepitup.magjobbackend.task.repository.api.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskDefaultServiceTest {

    private TaskDefaultService taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskDefaultService(taskRepository);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAll();

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger taskId = BigInteger.ONE;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        Optional<Task> result = taskService.find(taskId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(task, result.get());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testFindByTitle() {
        // Arrange
        String title = "Sample Task";
        Task task = new Task();
        task.setTitle(title);
        when(taskRepository.findByTitle(title)).thenReturn(Optional.of(task));

        // Act
        Optional<Task> result = taskService.findByTitle(title);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(task, result.get());
        verify(taskRepository, times(1)).findByTitle(title);
    }

    @Test
    void testFindAllByOrganization() {
        // Arrange
        Organization organization = new Organization();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAllByOrganization(organization)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAllByOrganization(organization);

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAllByOrganization(organization);
    }

    @Test
    void testFindAllByDateOfCreation() {
        // Arrange
        ZonedDateTime dateOfCreation = ZonedDateTime.now();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAllByDateOfCreation(dateOfCreation)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAllByDateOfCreation(dateOfCreation);

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAllByDateOfCreation(dateOfCreation);
    }

    @Test
    void testFindAllByDateOfCompletion() {
        // Arrange
        ZonedDateTime dateOfCompletion = ZonedDateTime.now();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAllByDateOfCompletion(dateOfCompletion)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAllByDateOfCompletion(dateOfCompletion);

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAllByDateOfCompletion(dateOfCompletion);
    }

    @Test
    void testFindAllByIsDone() {
        // Arrange
        boolean isDone = true;
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAllByIsDone(isDone)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAllByIsDone(isDone);

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAllByIsDone(isDone);
    }

    @Test
    void testFindAllByDescription() {
        // Arrange
        String description = "Sample description";
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAllByDescription(description)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAllByDescription(description);

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAllByDescription(description);
    }

    @Test
    void testFindAllByIsImportant() {
        // Arrange
        boolean isImportant = true;
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAllByIsImportant(isImportant)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAllByIsImportant(isImportant);

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAllByIsImportant(isImportant);
    }

    @Test
    void testFindAllByDeadLine() {
        // Arrange
        ZonedDateTime deadline = ZonedDateTime.now();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        tasks.add(new Task());
        when(taskRepository.findAllByDeadLine(deadline)).thenReturn(tasks);

        // Act
        List<Task> result = taskService.findAllByDeadLine(deadline);

        // Assert
        assertEquals(tasks.size(), result.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i), result.get(i));
        }
        verify(taskRepository, times(1)).findAllByDeadLine(deadline);
    }

    @Test
    void testCreate() {
        // Arrange
        Task task = new Task();

        // Act
        taskService.create(task);

        // Assert
        assertNotNull(task.getDateOfCreation());
        assertFalse(task.getIsDone());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger taskId = BigInteger.ONE;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        taskService.delete(taskId);

        // Assert
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testUpdate() {
        // Arrange
        Task task = new Task();

        // Act
        taskService.update(task);

        // Assert
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testCompleteTask() {
        // Arrange
        Task task = new Task();

        // Act
        taskService.completeTask(task);

        // Assert
        assertNotNull(task.getDateOfCompletion());
        assertTrue(task.getIsDone());
        verify(taskRepository, times(1)).save(task);
    }
}
