package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TaskTemplateRepository {

    private final List<TaskTemplate> data = new ArrayList<>();

    //CRUD
    public void create(TaskTemplate entity) {
        data.add(entity);
    }

    public void update(int id) {
        for (TaskTemplate taskTemplate : data) {
            if (Objects.equals(taskTemplate.getId(), id)) {
                //??
            }
        }
    }

    public TaskTemplate read(int id) throws TaskNotFoundException {
        return data.stream()
                .filter(taskTemplate -> Objects.equals(taskTemplate.getId(), id))
                .findFirst()
                .get();
    }

    public void delete(int id) throws TaskNotFoundException {
        data.removeIf(taskTemplate -> Objects.equals(taskTemplate.getId(), id));
    }

    //CUSTOM
    public void changeType(int id) {
        for (TaskTemplate taskTemplate : data) {
            if (Objects.equals(taskTemplate.getId(), id)) {
                if (taskTemplate.getTaskType().equals(TaskType.DEMO)) {
                    taskTemplate.setTaskType(TaskType.BUG);
                } else {
                    taskTemplate.setTaskType(TaskType.DEMO);
                }
            }
        }
    }
}