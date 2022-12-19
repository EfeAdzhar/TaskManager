package com.zoola.taskmanager.service;

import com.zoola.taskmanager.customExceptions.StatusException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.persistence.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository = new TaskRepository();

    //CRUD
    public void createOrUpdate(Task entity) {
        taskRepository.createOrUpdate(entity);
    }

    public Task read(int id) throws TaskNotFoundException {
        return taskRepository.read(id);
    }


    public void delete(int id) throws TaskNotFoundException {
        taskRepository.delete(id);
    }

    public Task findTask(int id) throws TaskNotFoundException {
        return taskRepository.findTask(id);
    }

    public String readAllTasks() {
        return taskRepository.readAllTasks();
    }

    public void setANewAssigneeForATask(int id) {
        taskRepository.setANewAssigneeForATask(id);
    }

    public void unassignTask(int id) {
        taskRepository.unassignTask(id);
    }

    public void changeStatusOfATask(int id) throws StatusException {
        taskRepository.changeStatusOfTask(id);
    }
}