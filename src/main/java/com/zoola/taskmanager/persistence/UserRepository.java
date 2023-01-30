package com.zoola.taskmanager.persistence;

import com.zoola.taskmanager.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserRepository {
  void create(@Param("user") User user);
  User read(@Param("id") int id);
  void update(@Param("id")int id, @Param("newUser") User newUser);
  void delete(@Param("id") int id);
}