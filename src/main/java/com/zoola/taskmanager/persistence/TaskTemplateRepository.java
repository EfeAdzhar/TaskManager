package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TaskTemplateRepository {

    private final List<TaskTemplate> data;

    //CRUD
    public void create(TaskTemplate entity) {
        data.add(entity);
    }

    public void update(int id, TaskTemplate newTaskTemplate) {
        for (int i = 0; i < data.size(); i++) {
            var oldTaskTemplate = data.get(i);
            if (Objects.equals(oldTaskTemplate.getId(), id)) {
                data.set(i, newTaskTemplate);
            }
        }
    }

    public TaskTemplate read(int id) throws TaskNotFoundException {
        try {
            return data.stream()
                    .filter(taskTemplate -> Objects.equals(taskTemplate.getId(), id))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException noSuchElementException) {
            throw new TaskNotFoundException();
        }
    }

    public void delete(int id) throws TaskNotFoundException {
        data.removeIf(taskTemplate -> Objects.equals(taskTemplate.getId(), id));
    }

    //CUSTOM
    public void changeType(int id, TaskType type) throws TaskNotFoundException {
        TaskTemplate taskTemplate = read(id);
        taskTemplate.setTaskType(type);
    }
}