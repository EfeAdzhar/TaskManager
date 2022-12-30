package com.zoola.taskmanager.controller;

import com.zoola.taskmanager.customExceptions.StatusException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/{id}")
    public Task getTask(@PathVariable int id) throws TaskNotFoundException {
       return taskService.read(id);
    }

    @GetMapping(path = "/all")
    public void getAllTasks() {
        taskService.readAllTasks();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createOrUpdateTask(@RequestBody Task task) {
        taskService.createOrUpdate(task);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable int id) throws TaskNotFoundException {
        taskService.delete(id);
    }

    @PutMapping(path = "/unassign/{id}")
    public void unassignTask(@PathVariable int id) throws TaskNotFoundException {
        taskService.unassignTask(id);
    }

    @PutMapping(path = "reassign/{id}/{userId}")
    public void reassignTask(@PathVariable int id, @PathVariable int userId) throws TaskNotFoundException {
        taskService.reassignTask(id, userId);
    }

    @PutMapping(path = "/status/{id}")
    public void changeTaskStatus(@PathVariable int id, @RequestBody TaskStatus taskStatus) throws StatusException, TaskNotFoundException {
        taskService.changeTaskStatus(id, taskStatus);
    }
}