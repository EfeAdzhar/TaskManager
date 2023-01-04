package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.customExceptions.StatusChangeException;
import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.customExceptions.UserNotFoundException;
import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final List<Task> data;

    private final UserRepository userRepository;

    //CRUD
    public void createOrUpdate(Task entity) {
        int currentVersion = data.stream()
                .filter(task -> Objects.equals(task.getId(), entity.getId()))
                .mapToInt(Task::getVersion)
                .max().orElse(0);
        entity.setVersion(currentVersion + 1);
        data.add(entity);
    }

    public Task read(int id) throws TaskNotFoundException {
        //Finds the final version of a task
        try {
            return data.stream()
                    .filter(task -> Objects.equals(id, task.getId()))
                    .max(Comparator.comparing(Task::getVersion))
                    .get();
        } catch (NoSuchElementException noSuchElementException) {
            throw new TaskNotFoundException();
        }
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
        task.setUserId(null);
    }

    public void reassignTask(int id, int userId) throws TaskNotFoundException, UserNotFoundException {
        Task task = read(id);
        User read = userRepository.read(userId);
        task.setUserId(read.getId());
    }

    public void changeTaskStatus(int id, TaskStatus taskStatus) throws StatusChangeException, TaskNotFoundException {
        Task task = read(id);
        if (task.getStatus().equals(TaskStatus.COMPLETED) || task.getStatus().equals(TaskStatus.FAILED)) {
            throw new StatusChangeException();
        } else {
            task.setStatus(taskStatus);
        }
    }
}