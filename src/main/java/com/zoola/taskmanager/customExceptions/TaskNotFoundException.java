package com.zoola.taskmanager.customExceptions;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
        super("No such task");
    }
}
