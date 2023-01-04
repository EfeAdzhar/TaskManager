package com.zoola.taskmanager.dto;

import com.zoola.taskmanager.domain.TaskStatus;
import lombok.Data;

@Data
public class TaskStatusDto {
    private TaskStatus taskStatus;
}