package com.zoola.taskmanager.service;

import com.zoola.taskmanager.customExceptions.StatusException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.persistence.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

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

    public String readAllTasks() {
        return taskRepository.readAllTasks();
    }

    public void unassignTask(int id) throws TaskNotFoundException {
        taskRepository.unassignTask(id);
    }

    public void reassignTask(int id, int userId) throws TaskNotFoundException {
        taskRepository.reassignTask(id, userId);
    }

    public void changeTaskStatus(int id, TaskStatus taskStatus) throws StatusException, TaskNotFoundException {
        taskRepository.changeTaskStatus(id, taskStatus);
    }
}