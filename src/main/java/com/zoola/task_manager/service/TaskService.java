package com.zoola.task_manager.service;

import com.zoola.task_manager.supportClasses.crudInterface.CRUD;
import com.zoola.task_manager.supportClasses.customExceptions.StatusException;
import com.zoola.task_manager.domain.Task;
import com.zoola.task_manager.supportClasses.customExceptions.TaskNotFoundException;
import com.zoola.task_manager.domain.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService implements CRUD<Task> {
    private final List<Task> taskList = new ArrayList<>();

    //CRUD
    @Override
    public void create(Task entity) {
        taskList.add(entity);
    }

    @Override
    public Task read(int id) {
        return taskList.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void update(int id) throws TaskNotFoundException {
        assert taskList.size() != 0;
        for (Task task : taskList) {
            if (task.getId() == id) {
                //
            } else {
                throw new TaskNotFoundException();
            }
        }
    }

    @Override
    public void delete(int id) throws TaskNotFoundException {
        assert taskList.size() != 0;
        for (Task taskToDelete : taskList) {
            if (taskToDelete.getId() == id) {
                taskList.remove(taskToDelete);
            } else {
                throw new TaskNotFoundException();
            }
        }
    }

    //CUSTOM METHODS FOR TASKS

    public String readAllTasks() {
        StringBuilder allTasks = new StringBuilder();
        assert taskList.size() != 0;
        for (Task task : taskList) {
            allTasks.append("\n").append(task.toString());
        }
        return allTasks.toString();
    }

    public void setANewAssigneeForATask(int id) {
        assert taskList.size() != 0;
        for (Task task : taskList) {
            if (task.getId() == id) {
                // if(taskList.contains(task)) {
                // task.setAssigneeId(
                // @Accessor(chaining = true)
                // new User().setId(id).getId())
                // }
            }
        }
    }

    //Questionable
    public void unassignTask(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                //???
                // task.setAssigneeId(new id);
            }
        }
    }

    public void changeStatusOfATask(int id) throws StatusException {
        for (Task task : taskList) {
            if (task.getId() == id) {
                if (task.getStatus().equals(TaskStatus.COMPLETED) || task.getStatus().equals(TaskStatus.FAILED)) {
                    throw new StatusException();
                } else {
                    // task.setStatus(TaskStatus.NEW || TaskStatus.IN_PROCESS);
                }
            }
        }
    }
}