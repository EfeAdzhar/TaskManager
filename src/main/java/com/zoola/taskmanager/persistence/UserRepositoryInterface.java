package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepositoryInterface {
  void create(@Param("user") User user);
  User read(@Param("id") int id);
  void update(@Param("id")int id, @Param("newUser") User newUser);
  void delete(@Param("id") int id);
}