package com.zoola.taskmanager.persistence.supportClasses;

import org.apache.ibatis.annotations.Param;

public interface CrudInterface<T> {
    void create(T t);
    T read(int id);
    void update(int id, T t);
    void delete(int id);
}
