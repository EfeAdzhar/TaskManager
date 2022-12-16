package com.zoola.task_manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private int id;
    private String email;
    private String name;
    //public Task createTask() {
      //  return new Task();
   //}
}