package com.zoola.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class TaskTemplate {
   private int id;
   private String name;
   private String description;
   private TaskType taskType;
}