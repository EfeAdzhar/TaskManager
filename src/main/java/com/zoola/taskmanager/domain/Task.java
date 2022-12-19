package com.zoola.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
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