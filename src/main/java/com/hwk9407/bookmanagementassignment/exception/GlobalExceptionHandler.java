package com.hwk9407.bookmanagementassignment.exception;

import com.hwk9407.bookmanagementassignment.exception.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = ErrorResponse.of(status.value(), LocalDateTime.now(), e.getMessage());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidIsbnException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIsbn(InvalidIsbnException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = ErrorResponse.of(status.value(), LocalDateTime.now(), e.getMessage());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = ErrorResponse.of(status.value(), LocalDateTime.now(), e.getMessage());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}
