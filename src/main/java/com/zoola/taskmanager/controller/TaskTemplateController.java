package com.zoola.taskmanager.controller;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.dto.TaskTypeDto;
import com.zoola.taskmanager.service.TaskTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/taskTemplate")
public class TaskTemplateController {

    private final TaskTemplateService taskTemplateService;

    @GetMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public TaskTemplate read(@PathVariable int id) throws TaskNotFoundException {
        return taskTemplateService.read(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody TaskTemplate entity) {
        taskTemplateService.create(entity);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public TaskTemplate update(@PathVariable int id, @RequestBody TaskTemplate newTaskTemplate) throws TaskNotFoundException {
        return taskTemplateService.update(id, newTaskTemplate);
    }

    @PutMapping(path = "/type/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void changeType(@PathVariable int id, @RequestBody TaskTypeDto taskTypeDto) throws TaskNotFoundException {
        TaskType newType = taskTypeDto.getTaskType();
        taskTemplateService.changeType(id, newType);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) throws TaskNotFoundException {
        taskTemplateService.delete(id);
    }
}