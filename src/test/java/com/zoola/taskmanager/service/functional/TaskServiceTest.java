package com.zoola.taskmanager.service.functional;

import com.zoola.taskmanager.customExceptions.StatusChangeException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.customExceptions.UserNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.UserRepository;
import com.zoola.taskmanager.service.TaskService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

@SpringBootTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskShouldBeCreatedAndRead(Task entity) throws TaskNotFoundException {
        taskService.createOrUpdate(entity);
        assertEquals(taskService.read(1), entity);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskShouldBeUpdated(Task entity) throws TaskNotFoundException  {
        taskService.createOrUpdate(entity);
        taskService.createOrUpdate(entity);
        assertEquals(taskService.read(1).getVersion(), 2);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskShouldBeDeleted(Task entity) throws TaskNotFoundException {
        taskService.createOrUpdate(entity);
        taskService.delete(1);
        assertThrows(TaskNotFoundException.class, () -> taskService.read(1));
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void allTaskShouldBeReturned(Task entity) {
        taskService.createOrUpdate(entity);
        assertFalse(taskService.readAllTasks().isBlank());
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskAssignShouldBeUnassigned(Task task) throws TaskNotFoundException {
        taskService.createOrUpdate(task);
        taskService.unassignTask(1);
        assertNull(taskService.read(1).getUserId());
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskAssignShouldBeReassigned(Task entity) throws TaskNotFoundException, UserNotFoundException {
        taskService.createOrUpdate(entity);
        when(userRepository.read(2)).thenReturn(new User(2, "email", "name"));
        taskService.reassignTask(1, 2);
        assertEquals(taskService.read(1).getUserId(), 2);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskAssignShouldNotReassignBecauseNoSuchUser(Task entity) throws UserNotFoundException {
        taskService.createOrUpdate(entity);
        when(userRepository.read(anyInt())).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> taskService.reassignTask(1, 2));
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskShouldChangeStatus(Task entity) throws TaskNotFoundException, StatusChangeException {
        taskService.createOrUpdate(entity);
        taskService.changeTaskStatus(1, TaskStatus.IN_PROGRESS);
        assertEquals(taskService.read(1).getStatus().toString(), TaskStatus.IN_PROGRESS.toString());
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskChangeStatusShouldThrowException(Task entity) {
        entity.setStatus(TaskStatus.COMPLETED);
        taskService.createOrUpdate(entity);
        assertThrows(StatusChangeException.class, () -> taskService.changeTaskStatus(1, TaskStatus.IN_PROGRESS));
    }

    public static List<Arguments> createTaskTemplate() {
        return List.of(
                Arguments.of(new Task(1, "task-1",
                        "task number 1", TaskStatus.NEW,
                        1, 1, 1, 1)));
    }
}