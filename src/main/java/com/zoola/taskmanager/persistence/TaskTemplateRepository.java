package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.customExceptions.TaskNotFoundException;
import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TaskTemplateRepository {

    private final List<TaskTemplate> data = new ArrayList<>();

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
        /**@bug(FIXME: Throwing noSuchElementException instead of TaskNotFoundException**/
        return Optional.of(data.stream()
                .filter(taskTemplate -> Objects.equals(taskTemplate.getId(), id))
                .findFirst()
                .get()).orElseThrow(TaskNotFoundException::new);
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