package com.zoola.taskmanager.service;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.persistence.TaskTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTemplateService {

    private final TaskTemplateRepository taskTemplateRepository;

    public void create(TaskTemplate entity) {
        taskTemplateRepository.create(entity);
    }

    public TaskTemplate read(int id) throws TaskNotFoundException {
        return taskTemplateRepository.read(id);
    }

    public void update(int id) throws TaskNotFoundException {
        taskTemplateRepository.update(id);
    }

    public void delete(int id) throws TaskNotFoundException {
        taskTemplateRepository.delete(id);
    }

    public void changeType(int id, TaskType type) throws TaskNotFoundException {
        taskTemplateRepository.changeType(id, type);
    }
}