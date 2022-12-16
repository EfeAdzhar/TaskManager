package com.zoola.task_manager.supportClasses.customExceptions;

public class StatusException extends Exception {
    public StatusException() {
        super("Task status is completed or failed");
    }
}
