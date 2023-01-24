package com.zoola.taskmanager.rest.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoola.taskmanager.controller.TaskTemplateController;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.dto.TaskStatusDto;
import com.zoola.taskmanager.persistence.TaskRepository;
import com.zoola.taskmanager.persistence.UserRepository;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@WebMvcTest(TaskTemplateController.class)
@ComponentScan("com.zoola.taskmanager")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void getTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);

        //When
        String stringTask = mockMvc.perform(MockMvcRequestBuilders.get("/task/1")
                        .content(objectMapper.writeValueAsString(task)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Task actualTask = objectMapper.readValue(stringTask, Task.class);

        //Then
        assertEquals(taskRepository.read(1), actualTask);
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void createTask(Task task) throws Exception {
        //When
        mockMvc.perform(post("/task")
                .content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        assertEquals(taskRepository.read(1), task);
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void updateTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);

        //When
        mockMvc.perform(post("/task")
                .content(objectMapper.writeValueAsString(task))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        assertEquals(taskRepository.read(1).getVersion(), 2);
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void deleteTask() throws Exception {
        //When
        mockMvc.perform(delete("/task/1"));

        //Then
        assertThrows(TaskNotFoundException.class, () -> taskRepository.read(1));
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void unassignTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);

        //When
        mockMvc.perform(put("/task/unassign/1")).andExpect(status().isOk());

        //Then
        assertNull(taskRepository.read(1).getUserId());
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void reassignTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);
        User user = new User(2, "email", "name");
        when(userRepository.read(2)).thenReturn(user);

        //When
        mockMvc.perform(put("/task/reassign/1/2")).andExpect(status().isOk());

        //Then
        assertEquals(taskRepository.read(1).getUserId(), user.getId());
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void changeStatusOfTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);
        TaskStatusDto taskStatusDto = new TaskStatusDto();
        taskStatusDto.setTaskStatus(TaskStatus.COMPLETED);

        //When
       mockMvc.perform(put("/task/status/1").content(objectMapper.writeValueAsString(taskStatusDto))
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

       //Then
        assertEquals(taskRepository.read(1).getStatus(), TaskStatus.COMPLETED);

    }

    public static List<Task> creatingTask() {
        return List.of(
                new Task(1, "task-1", "first task",
                        TaskStatus.NEW, 1, 1, 1, 1));
    }
}