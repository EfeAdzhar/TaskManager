package com.zoola.task_manager.supportClasses.crudInterface;

public interface CRUD<T> {
     void create(T entity);
     T read(int id) throws Exception;
     void update(int id) throws Exception;
     void delete(int id) throws Exception;
}