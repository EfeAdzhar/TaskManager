package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.domain.TaskTemplate;
import com.zoola.taskmanager.domain.TaskType;
import com.zoola.taskmanager.persistence.supportClasses.CrudInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskTemplateRepository extends CrudInterface<TaskTemplate> {
    @Override
    void create(@Param("entity") TaskTemplate taskTemplate);

    @Override
    TaskTemplate read(@Param("id") int id);

    @Override
    void update(@Param("id") int id, @Param("newTaskTemplate") TaskTemplate taskTemplate);

    @Override
    void delete(@Param("id") int id);

    void changeType(@Param("id") int id, @Param("type") TaskType type);
}