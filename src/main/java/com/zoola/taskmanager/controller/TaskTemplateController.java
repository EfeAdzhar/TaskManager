package com.zoola.taskmanager.controller;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.service.TaskTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/taskTemplate")
public class TaskTemplateController {

    @Autowired
    private TaskTemplateService taskTemplateService;

    @GetMapping(path = "/get/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public TaskTemplate read(@PathVariable int id) throws TaskNotFoundException {
        return taskTemplateService.read(id);
    }

    @PostMapping(path = "/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody TaskTemplate entity) {
        taskTemplateService.create(entity);
    }

    @PutMapping(path = "/update/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@PathVariable int id, @RequestBody TaskTemplate newTaskTemplate) throws TaskNotFoundException {
        taskTemplateService.update(id, newTaskTemplate);
    }

    /**
     * @param(FIXME: 21.12.2022) changeType: Can't get enum from request
     **/
    @PutMapping(path = "/changeType/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void changeType(@PathVariable int id, @RequestBody Map<String, TaskType> type) throws TaskNotFoundException {
        Optional<TaskType> taskType = Optional.empty();
        for (Map.Entry<String, TaskType> entry : type.entrySet()) {
            taskType = Optional.of(TaskType.valueOf(String.valueOf(entry.getValue())));
        }
        if (taskType.isPresent()) {
            taskTemplateService.changeType(id, taskType.get());
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable int id) throws TaskNotFoundException {
        taskTemplateService.delete(id);
    }
}