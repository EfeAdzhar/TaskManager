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
    public void testTaskIsCreated(@Mock Task task) {
        taskService.createOrUpdate(task);
        Mockito.verify(taskRepository).createOrUpdate(task);
    }

    @Test
    public void testTaskIsRead(@Mock Task task) throws TaskNotFoundException {
        //given
        when(taskRepository.read(1)).thenReturn(task);

        //when
        Task readTask = taskService.read(1);

        //then
        Mockito.verify(taskRepository).read(1);
        assertEquals(readTask, task);
    }

    @Test
    public void testTaskIsDeleted(@Mock Task task) throws TaskNotFoundException {
        //given
        int id = 1;

        //when
        taskService.delete(id);

        //then
        Mockito.verify(taskRepository).delete(id);
    }

    @Test
    public void testTaskReadAllTasks() {
        //given
        when(taskRepository.readAllTasks()).thenReturn("All tasks");

        //when
        String allTasks = taskService.readAllTasks();

        //then
        Mockito.verify(taskRepository).readAllTasks();
        assertEquals(allTasks, "All tasks");
    }

    @Test
    public void testUnassignTask(@Mock Task task) throws TaskNotFoundException {
        //given
        int id = 1;

        //when
        taskService.unassignTask(id);

        //then
        Mockito.verify(taskRepository).unassignTask(id);
    }

    @Test
    public void testReassignTask(@Mock Task task) throws TaskNotFoundException {
        //given
        int id = 1;
        int userId = 1;

        //when
        taskService.reassignTask(id, userId);

        //then
        Mockito.verify(taskRepository).reassignTask(id, userId);
    }

    @Test
    public void testChangeTaskStatus() throws StatusException, TaskNotFoundException {
        //given
        int id = 1;

        //when
        taskService.changeTaskStatus(id, TaskStatus.COMPLETED);

        //then
        Mockito.verify(taskRepository).changeTaskStatus(id, TaskStatus.COMPLETED);
    }
}