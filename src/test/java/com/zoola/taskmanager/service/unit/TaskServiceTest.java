package com.zoola.taskmanager.service.unit;

import com.zoola.taskmanager.customExceptions.StatusException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.persistence.TaskRepository;
import com.zoola.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@MockitoSettings
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testTaskIsCreated(@Mock Task task) throws TaskNotFoundException {
        when(taskRepository.read(anyInt())).thenReturn(task);
        assertEquals(taskService.read(1), task);
    }

    @Test
    public void testTaskIsRead(@Mock Task task) throws TaskNotFoundException {
        when(taskRepository.read(anyInt())).thenReturn(task);
        assertEquals(taskService.read(1), task);
    }

    @Test
    public void testTaskIsDeleted(@Mock Task task) throws TaskNotFoundException {
        when(taskRepository.read(anyInt())).thenReturn(task);
        taskRepository.delete(1);
    }

    @Test
    public void testTaskReadAllTasks() {
       when(taskRepository.readAllTasks()).thenReturn("Here all tasks");
       assertFalse(taskService.readAllTasks().isEmpty());
    }

    @Test
    public void testUnassignTask(@Mock Task task) throws TaskNotFoundException {
        when(taskRepository.read(anyInt())).thenReturn(task);
        taskRepository.unassignTask(1);
    }

    @Test
    public void testReassignTask(@Mock Task task) throws TaskNotFoundException {
        when(taskRepository.read(anyInt())).thenReturn(task);
        taskRepository.reassignTask(1, 1);
    }

    @Test
    public void testChangeTaskStatus(@Mock Task task) throws StatusException, TaskNotFoundException {
        when(taskRepository.read(anyInt())).thenReturn(task);
        taskRepository.changeTaskStatus(1, TaskStatus.COMPLETED);
    }
}