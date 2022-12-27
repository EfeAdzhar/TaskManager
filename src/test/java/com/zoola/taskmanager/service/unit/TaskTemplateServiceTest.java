package com.zoola.taskmanager.service.unit;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.persistence.TaskTemplateRepository;
import com.zoola.taskmanager.service.TaskTemplateService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@MockitoSettings
public class TaskTemplateServiceTest {

    @Mock
    private TaskTemplateRepository taskTemplateRepository;

    @InjectMocks
    private TaskTemplateService taskTemplateService;

    @Test
    public void testTaskTemplateIsCreated(@Mock TaskTemplate taskTemplate) {
        taskTemplateService.create(taskTemplate);
        Mockito.verify(taskTemplateRepository).create(taskTemplate);
    }

    @Test
    public void testTaskTemplateIsRead(@Mock TaskTemplate taskTemplate) throws TaskNotFoundException {
        when(taskTemplateRepository.read(1)).thenReturn(taskTemplate);
        assertEquals(taskTemplateService.read(1), taskTemplate);
    }

    @Test
    public void testTaskTemplateIsUpdated(@Mock TaskTemplate taskTemplate, @Mock TaskTemplate newTaskTemplate) throws TaskNotFoundException {
        //given
        when(taskTemplateRepository.read(1)).thenReturn(newTaskTemplate);

        //when
        TaskTemplate actualTaskTemplate = taskTemplateService.update(1, taskTemplate);

        //then
        Mockito.verify(taskTemplateRepository).update(1, taskTemplate);
        assertEquals(actualTaskTemplate, newTaskTemplate);
    }

    @Test
    public void testTaskTemplateIsDeleted() throws TaskNotFoundException {
        //given
        int id = 1;

        //when
        taskTemplateService.delete(id);

        //then
        Mockito.verify(taskTemplateRepository).delete(id);
    }

    @Test
    public void testTaskTemplateChangedTypes() throws TaskNotFoundException {
        //given
        int id = 1;

        //when
        taskTemplateService.changeType(id, TaskType.DEMO);

        //then
        Mockito.verify(taskTemplateRepository).changeType(id, TaskType.DEMO);
    }
}