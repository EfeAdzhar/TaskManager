package com.zoola.taskmanager.service;

import com.zoola.taskmanager.customExceptions.StatusException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskShouldBeCreatedAndRead(Task entity) throws TaskNotFoundException {
        taskService.createOrUpdate(entity);
        assertEquals(taskService.read(1), entity);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskShouldBeDeleted(Task entity) throws TaskNotFoundException {
        taskService.createOrUpdate(entity);
        taskService.delete(1);
        assertThrows(NoSuchElementException.class, () -> taskService.read(1));
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void allTaskShouldBeReturned(Task entity) {
        taskService.createOrUpdate(entity);
        assertFalse(taskService.readAllTasks().isBlank());
    }

    /**@bug(FIXME: java.lang.NumberFormatException: For input string: " ") on taskAssignShouldBeUnassigned*/
    /*
    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskAssignShouldBeUnassigned(Task task) throws TaskNotFoundException {
        taskService.createOrUpdate(task);
        taskService.unassignTask(1);
        assertThrows(TaskNotFoundException.class, () -> taskService.read(1));
    }
     */

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskAssignShouldBeReassigned(Task entity) throws TaskNotFoundException {
        taskService.createOrUpdate(entity);
        taskService.reassignTask(1, 2);
        assertEquals(taskService.read(1).getUserId(), 2);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskShouldChangeStatus(Task entity) throws TaskNotFoundException, StatusException {
        taskService.createOrUpdate(entity);
        taskService.changeTaskStatus(1, TaskStatus.IN_PROGRESS);
        assertEquals(taskService.read(1).getStatus().toString(), TaskStatus.IN_PROGRESS.toString());
    }

    public static List<Arguments> createTaskTemplate() {
        return List.of(
                Arguments.of(new Task(1, "task-1",
                        "task number 1", TaskStatus.NEW,
                        1, 1, 1, 1)));
    }
}