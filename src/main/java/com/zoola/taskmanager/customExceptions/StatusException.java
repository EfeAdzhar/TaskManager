package com.zoola.taskmanager.customExceptions;

public class StatusException extends Exception {
    public StatusException() {
        super("Task status is completed or failed");
    }
}
