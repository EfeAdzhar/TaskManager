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
        for (Task task : data) {
            if (Objects.equals(entity, task)) {
                task.setVersion(task.getVersion() + 1);
                break;
            }
        }
        data.add(entity);
    }

    public Task read(int id) throws TaskNotFoundException {
        return data.stream()
                .filter(task -> Objects.equals(task.getId(), id))
                .findFirst()
                .get();
    }

    public void delete(int id) throws TaskNotFoundException {
        data.removeIf(task -> Objects.equals(task.getId(), id));
    }

    //CUSTOM
    public Task findTask(int id) throws TaskNotFoundException {
        //Finds the final version of a task
        return data.stream()
                .max(Comparator.comparing(Task::getVersion))
                .get();
    }

    public String readAllTasks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : data) {
            stringBuilder.append(task).append("/n");
        }
        return stringBuilder.toString();
    }

    public void setANewAssigneeForATask(int id) {
        // assert taskList.size() != 0;
        //for (Task task : taskList) {
        //  if (task.getId() == id) {
        // if(taskList.contains(task)) {
        // task.setAssigneeId(
        // @Accessor(chaining = true)
        // new User().setId(id).getId())
        // }
    }

    //Questionable
    public void unassignTask(int id) {
        // for (Task task : taskList) {
        //   if (task.getId() == id) {
        //???
        // task.setAssigneeId(new id);
    }

    public void changeStatusOfTask(int id) throws StatusException {
        for (Task task : data) {
            if (task.getId() == id) {
                if (task.getStatus().equals(TaskStatus.COMPLETED) || task.getStatus().equals(TaskStatus.FAILED)) {
                    throw new StatusException();
                } else {
                    if (task.getStatus().equals(TaskStatus.NEW)) {
                        task.setStatus(TaskStatus.IN_PROGRESS);
                    } else if (task.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                        task.setStatus(TaskStatus.NEW);
                    }
                }
            }
        }
    }
}