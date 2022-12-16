package com.zoola.task_manager.domain;

import lombok.Data;

@Data
public class TaskTemplate {
   private int id;
   private String name;
   private String description;
   private TaskType taskType;
}