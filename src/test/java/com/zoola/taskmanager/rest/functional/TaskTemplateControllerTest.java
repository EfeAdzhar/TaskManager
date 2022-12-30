package com.zoola.taskmanager.rest.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoola.taskmanager.controller.TaskTemplateController;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.persistence.TaskTemplateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskTemplateController.class)
@ComponentScan("com.zoola.taskmanager")
public class TaskTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Test
    public void getTaskTemplate() throws Exception {
        //Given
        TaskTemplate taskTemplate = new TaskTemplate(1, "taskTemplate-1", "first task", TaskType.DEMO);
        taskTemplateRepository.create(taskTemplate);

        //When
        String taskTemplateString = mockMvc.perform(MockMvcRequestBuilders.get("/taskTemplate/1"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        TaskTemplate expectedTaskTemplate = toObject(TaskTemplate.class, taskTemplateString);

        //Then
        assertEquals(taskTemplate, expectedTaskTemplate);
    }

    @Test
    public void createTaskTemplate() throws Exception {
        //Give
        TaskTemplate taskTemplate = new TaskTemplate(1, "taskTemplate-1", "first task", TaskType.DEMO);

        //When
        mockMvc.perform(post("/taskTemplate")
                .content(asJsonString(taskTemplate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //Then
        assertEquals(taskTemplateRepository.read(1), taskTemplate);
    }

    @Test
    public void updateTaskTemplate() throws Exception {
        //Given
        TaskTemplate oldTaskTemplate = new TaskTemplate(1, "taskTemplate-1", "first task", TaskType.DEMO);
        TaskTemplate newTaskTemplate = new TaskTemplate(1, "newTaskTemplate-1", "new first task", TaskType.DEMO);
        taskTemplateRepository.create(oldTaskTemplate);

        //When
        mockMvc.perform(put("/taskTemplate/1")
                .content(asJsonString(newTaskTemplate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        //Then
        assertEquals(taskTemplateRepository.read(1), newTaskTemplate);
    }

    @Test
    public void updateTaskTemplateType() throws Exception {
        //Given
        TaskTemplate oldTaskTemplate = new TaskTemplate(1, "taskTemplate-1", "first task", TaskType.DEMO);
        Map<String, TaskType> taskTypeMap = new HashMap<>();
        taskTypeMap.put("type", TaskType.BUG);
        taskTemplateRepository.create(oldTaskTemplate);

        //When
        mockMvc.perform(put("/taskTemplate/type/1").content(asJsonString(taskTypeMap))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        //Then
        assertEquals(taskTemplateRepository.read(1).getTaskType(), TaskType.BUG);
    }

    @Test
    public void deleteTaskTemplate() throws Exception {
        //Given
        TaskTemplate taskTemplate = new TaskTemplate(1, "taskTemplate-1", "first task", TaskType.DEMO);
        taskTemplateRepository.create(taskTemplate);

        //When
        mockMvc.perform(delete("/taskTemplate/1"))
                .andExpect(status().isAccepted());

        //Then
        assertThrows(NoSuchElementException.class, () -> taskTemplateRepository.read(1));
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