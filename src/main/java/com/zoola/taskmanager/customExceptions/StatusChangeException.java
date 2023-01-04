package com.zoola.taskmanager.customExceptions;

public class StatusChangeException extends Exception {
    public StatusChangeException() {
        super("Task status is completed or failed");
    }
}
