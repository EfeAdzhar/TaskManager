package com.zoola.taskmanager.service;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.persistence.TaskTemplateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTemplateServiceTest {

    private TaskTemplateService taskTemplateService;
    @Mock
    private TaskTemplateRepository taskTemplateRepository;

    @Test
    public void taskTemplateReadThrowsException() throws TaskNotFoundException {
       assertThrows(taskTemplateService.read(1).getClass().asSubclass(Class.class), TaskNotFoundException::new);
    }
}
