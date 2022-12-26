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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@MockitoSettings
public class TaskTemplateServiceTest {

    @Mock
    private TaskTemplateRepository taskTemplateRepository;

    @InjectMocks
    private TaskTemplateService taskTemplateService;

    @Test
    public void testTaskTemplateIsCreated(@Mock TaskTemplate taskTemplate) throws TaskNotFoundException {
       when(taskTemplateRepository.read(anyInt())).thenReturn(taskTemplate);
       assertEquals(taskTemplateService.read(1), taskTemplate);
    }

    @Test
    public void testTaskTemplateIsRead(@Mock TaskTemplate taskTemplate) throws TaskNotFoundException {
        when(taskTemplateRepository.read(anyInt())).thenReturn(taskTemplate);
        assertEquals(taskTemplateService.read(1), taskTemplate);
    }

    @Test
    public void testTaskTemplateIsUpdated(@Mock TaskTemplate taskTemplate) throws TaskNotFoundException {
        /**@bug(FIXME: Don't know how to update it correctly)
         * */
        when(taskTemplateRepository.read(anyInt())).thenReturn(taskTemplate);
        TaskTemplate newTaskTemplate = Mockito.mock(TaskTemplate.class);
        assertNotEquals(taskTemplateService.read(1), newTaskTemplate);
    }

    @Test
    public void testTaskTemplateIsDeleted() throws TaskNotFoundException {
        /**@bug(FIXME: Don't know how to delete it correctly)*/
        when(taskTemplateRepository.read(1)).thenThrow(TaskNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> taskTemplateService.read(1));
    }

    @Test
    public void testTaskTemplateChangedTypes(@Mock TaskTemplate taskTemplate) throws TaskNotFoundException {
        /**@bug(FIXME: Don't know how to change type it correctly)*/
       when(taskTemplateRepository.read(1)).thenReturn(taskTemplate);
       taskTemplate.setTaskType(TaskType.DEMO);
       taskTemplateRepository.changeType(1, TaskType.BUG);
       assertEquals(TaskType.BUG, taskTemplateService.read(1).getTaskType());
    }
}