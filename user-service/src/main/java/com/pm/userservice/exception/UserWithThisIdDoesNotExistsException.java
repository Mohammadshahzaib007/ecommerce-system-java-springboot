package com.pm.userservice.exception;

public class UserWithThisIdDoesNotExistsException extends RuntimeException {
    public UserWithThisIdDoesNotExistsException(String message) {
        super(message);
    }
}
