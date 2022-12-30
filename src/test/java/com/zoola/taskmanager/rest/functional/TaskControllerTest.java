package com.zoola.taskmanager.rest.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoola.taskmanager.controller.TaskTemplateController;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.persistence.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(TaskTemplateController.class)
@ComponentScan("com.zoola.taskmanager")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void getTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);

        //When
        String stringTask = mockMvc.perform(MockMvcRequestBuilders.get("/task/1")
                        .content(asJsonString(task)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Task actualTask = toObject(Task.class, stringTask);

        //Then
        assertEquals(taskRepository.read(1), actualTask);
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void createTask(Task task) throws Exception {
        //When
        mockMvc.perform(post("/task")
                .content(asJsonString(task))
                .contentType(MediaType.APPLICATION_JSON));

        //Then
        assertEquals(taskRepository.read(1), task);
    }

    @ParameterizedTest
    @MethodSource("creatingTask")
    public void deleteTask() throws Exception {
        //When
        mockMvc.perform(delete("/task/1"));

        //Then
        assertThrows(NoSuchElementException.class, () -> taskRepository.read(1));
    }

    /**@// FIXME: 30.12.2022 unassignTask */
    @ParameterizedTest
    @MethodSource("creatingTask")
    public void unassignTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);

        //When
        mockMvc.perform(put("/unassign/1")).andExpect(status().isAccepted());

        //Then
        assertEquals(taskRepository.read(1).getId(), "");
    }

    /**@// FIXME: 30.12.2022 reassignTask */
    @ParameterizedTest
    @MethodSource("creatingTask")
    public void reassignTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);

        //When
        mockMvc.perform(put("reassign/1/2")).andExpect(status().isAccepted());

        //Then
        assertEquals(taskRepository.read(1).getUserId(), 2);
    }

    /**@bug(FIXME: changeStatusOfTask*/
    @ParameterizedTest
    @MethodSource("creatingTask")
    public void changeStatusOfTask(Task task) throws Exception {
        //Given
        taskRepository.createOrUpdate(task);

        //When
       mockMvc.perform(put("/status/1").content(asJsonString(" ")));

    }


    public List<Task> creatingTask() {
        return List.of(new Task(1, "task-1", "first task", TaskStatus.NEW, 1, 1, 1, 1));
    }

      /*
    Source:
    http://www.masterspringboot.com/testing/testing-spring-boot-applications-with-mockmvc/
    */
    /**@// *FIXME: 29.12.2022 ObjectMapper should be a bean*/
    public static String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static <T> T toObject(Class<T> clazz, String content) throws JsonProcessingException {
        return new ObjectMapper().readValue(content, clazz);
    }
}
