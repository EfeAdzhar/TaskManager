package com.zoola.task_manager.service;

import com.zoola.task_manager.domain.TaskTemplate;
import com.zoola.task_manager.supportClasses.crudInterface.CRUD;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskTemplateService implements CRUD<TaskTemplate> {
    private final List<TaskTemplate> taskTemplateList = new ArrayList<>();

    @Override
    public void create(TaskTemplate entity) {
        taskTemplateList.add(entity);
    }

    @Override
    public TaskTemplate read(int id) throws Exception {
        return taskTemplateList.stream()
                .filter(taskTemplate -> taskTemplate.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void update(int id) throws Exception {
        for(TaskTemplate taskTemplate : taskTemplateList) {
            if(taskTemplate.getId() == id) {
                //UPDATE
            }
        }

    }

    @Override
    public void delete(int id) throws Exception {
        taskTemplateList.removeIf(taskTemplate -> taskTemplate.getId() == id);
    }
}
