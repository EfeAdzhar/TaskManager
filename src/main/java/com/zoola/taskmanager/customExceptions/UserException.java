package com.zoola.taskmanager.customExceptions;

public class UserException extends Exception {
    public UserException(String message) {
        super("User not found");
    }
}
