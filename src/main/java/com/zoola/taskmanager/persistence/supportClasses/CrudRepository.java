package com.zoola.taskmanager.persistence.supportClasses;

public interface CrudRepository<T> {
    void create(T t);
    T read(int id);
    void update(int id, T t);
    void delete(int id);
}
