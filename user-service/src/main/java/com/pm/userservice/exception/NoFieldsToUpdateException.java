package com.pm.userservice.exception;

public class NoFieldsToUpdateException extends RuntimeException {
    public NoFieldsToUpdateException(String message) {
        super(message);
    }
}
