package com.zoola.taskmanager.rest.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoola.taskmanager.controller.TaskTemplateController;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.dto.TaskTypeDto;
import com.zoola.taskmanager.persistence.TaskTemplateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskTemplateController.class)
@ComponentScan("com.zoola.taskmanager")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskTemplateRepository taskTemplateRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
        TaskTemplate expectedTaskTemplate = objectMapper.readValue(taskTemplateString, TaskTemplate.class);

        //Then
        assertEquals(taskTemplate, expectedTaskTemplate);
    }

    @Test
    public void createTaskTemplate() throws Exception {
        //Give
        TaskTemplate taskTemplate = new TaskTemplate(1, "taskTemplate-1", "first task", TaskType.DEMO);

        //When
        mockMvc.perform(post("/taskTemplate")
                .content(objectMapper.writeValueAsString(taskTemplate))
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
                .content(objectMapper.writeValueAsString(newTaskTemplate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        //Then
        assertEquals(taskTemplateRepository.read(1), newTaskTemplate);
    }

    @Test
    public void updateTaskTemplateType() throws Exception {
        //Given
        TaskTemplate taskTemplate = new TaskTemplate(1, "taskTemplate-1", "first task", TaskType.DEMO);
        TaskTypeDto taskTypeDto = new TaskTypeDto();
        taskTypeDto.setTaskType(TaskType.BUG);
        taskTemplateRepository.create(taskTemplate);

        //When
        mockMvc.perform(put("/taskTemplate/type/1").content(objectMapper.writeValueAsString(taskTypeDto))
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
        assertThrows(TaskNotFoundException.class, () -> taskTemplateRepository.read(1));
    }
}