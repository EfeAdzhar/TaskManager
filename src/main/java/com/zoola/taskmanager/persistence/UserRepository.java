package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.domain.User;
import com.zoola.taskmanager.persistence.supportClasses.CrudInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository extends CrudInterface<User> {
    @Override
    void create(@Param("user") User user);

    @Override
    User read(@Param("id") int id);

    @Override
    void update(@Param("id") int id, @Param("newUser") User newUser);

    @Override
    void delete(@Param("id") int id);
}