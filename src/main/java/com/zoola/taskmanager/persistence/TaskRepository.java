package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.domain.Task;
import com.zoola.taskmanager.domain.TaskStatus;
import com.zoola.taskmanager.persistence.supportClasses.CrudRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskRepository extends CrudRepository<Task> {

    void createOrUpdate(@Param("entity") Task task);

    @Override
    Task read(int id);

    @Override
    void delete(int id);

    String readAllTasks();

    void unassignTask(@Param("id") int id);

    void reassignTask(@Param("id") int id, @Param("userId") int userId);

    void changeTaskStatus(@Param("id") int id, @Param("taskStatus") TaskStatus taskStatus);
}