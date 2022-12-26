package com.zoola.taskmanager.service.functional;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.service.TaskTemplateService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskTemplateServiceTest {

    @Autowired
    private TaskTemplateService taskTemplateService;

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskTemplateShouldBeCreatedAndRead(TaskTemplate entity) throws TaskNotFoundException {
        taskTemplateService.create(entity);
        assertEquals(taskTemplateService.read(1), entity);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskTemplateShouldBeUpdated(TaskTemplate entity) throws TaskNotFoundException {
        TaskTemplate newTaskTemplate = new TaskTemplate(1, "new-template-1", "template number 1", TaskType.BUG);
        taskTemplateService.create(entity);
        taskTemplateService.update(1, newTaskTemplate);
        assertNotEquals(taskTemplateService.read(1), entity);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void taskTemplateShouldChangeStatus(TaskTemplate entity) throws TaskNotFoundException {
        taskTemplateService.create(entity);
        taskTemplateService.changeType(1, TaskType.BUG);
        assertEquals(taskTemplateService.read(1).getTaskType(), TaskType.BUG);
    }

    @ParameterizedTest
    @MethodSource("createTaskTemplate")
    public void delete(TaskTemplate entity) throws TaskNotFoundException {
        taskTemplateService.create(entity);
        taskTemplateService.delete(1);
        assertThrows(NoSuchElementException.class, () -> taskTemplateService.read(1));
    }

    public static List<Arguments> createTaskTemplate() {
        return List.of(
                Arguments.of(new TaskTemplate(1, "template-1",
                        "template number 1", TaskType.DEMO)));
    }
}