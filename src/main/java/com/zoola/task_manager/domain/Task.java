package com.zoola.task_manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private int id;
    private String name;
    private String description;
    private TaskStatus status;
    private int taskTemplateId;
    private int userId;
    private int assigneeId;
    private int version;
}