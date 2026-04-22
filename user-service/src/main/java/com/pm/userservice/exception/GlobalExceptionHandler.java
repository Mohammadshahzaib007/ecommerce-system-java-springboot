package com.pm.userservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                (error) -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        Map<String, String> errors = new HashMap<>();

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {

            // Field name (safe extraction)
            String fieldName = ife.getPath().isEmpty()
                    ? "unknown"
                    : ife.getPath().getFirst().getPropertyName();

            // Invalid value
            Object invalidValue = ife.getValue();

            // Target type (e.g. enum)
            Class<?> targetType = ife.getTargetType();

            // Check if it's an enum
            if (targetType.isEnum()) {
                Object[] allowedValues = targetType.getEnumConstants();

                errors.put(fieldName,
                        "Invalid value '" + invalidValue +
                                "'. Allowed values: " + Arrays.toString(allowedValues));
            } else {
                errors.put(fieldName,
                        "Invalid value '" + invalidValue + "'");
            }
        } else {
            errors.put("error", "Malformed JSON request");
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRoleAlreadyExistsException(
            RoleAlreadyExistsException ex) {

        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleDoesNotExistException.class)
    public ResponseEntity<Map<String, String>> roleDoestNotExistException(RoleDoesNotExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        log.warn(ex.getMessage());
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserWithThisIdDoesNotExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserWithThisIdDoesNotExistsException(
            UserWithThisIdDoesNotExistsException ex) {
        log.warn(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(NoFieldsToUpdateException.class)
    public ResponseEntity<Map<String, String>> handleNoFieldsToUpdateException(NoFieldsToUpdateException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
