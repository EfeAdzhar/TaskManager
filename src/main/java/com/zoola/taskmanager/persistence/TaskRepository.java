package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.customExceptions.StatusException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepository {

    private final List<Task> data = new ArrayList<>();

    //CRUD
    public void createOrUpdate(Task entity) {
        int currentVersion = data.stream()
                .filter(task->Objects.equals(task.getId(), entity.getId()))
                .mapToInt(Task::getVersion)
                .max().orElse(0);
        entity.setVersion(currentVersion + 1);
        data.add(entity);
    }

    public Task read(int id) throws TaskNotFoundException {
        //Finds the final version of a task
        return data.stream()
                .filter(task -> Objects.equals(id, task.getId()))
                .max(Comparator.comparing(Task::getVersion))
                .get();
    }

    public void delete(int id) throws TaskNotFoundException {
        data.removeIf(task -> Objects.equals(task.getId(), id));
    }

    //CUSTOM
    public String readAllTasks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : data) {
            stringBuilder.append(task).append("/n");
        }
        return stringBuilder.toString();
    }

    public void unassignTask(int id) throws TaskNotFoundException {
        Task task = read(id);
        task.setUserId(Integer.parseInt(""));
    }

    public void reassignTask(int id, int userId) throws TaskNotFoundException {
        Task task = read(id);
        task.setUserId(userId);
    }

    public void changeTaskStatus(int id, TaskStatus taskStatus) throws StatusException, TaskNotFoundException {
        Task task = read(id);
        task.setStatus(taskStatus);
    }
}