package com.zoola.task_manager.supportClasses.customExceptions;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException() {
        super("No such task");
    }
}
