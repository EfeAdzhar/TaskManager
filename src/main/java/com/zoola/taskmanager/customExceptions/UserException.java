package com.zoola.taskmanager.customExceptions;

public class UserException extends Exception {
    public UserException() {
        super("User not found");
    }
}
