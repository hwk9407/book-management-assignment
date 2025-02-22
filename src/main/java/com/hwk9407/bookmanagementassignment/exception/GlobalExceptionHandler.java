package com.hwk9407.bookmanagementassignment.exception;

import com.hwk9407.bookmanagementassignment.exception.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errorMessages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " 필드는 " + fieldError.getDefaultMessage())
                .toList();
        ErrorResponse errorResponse = ErrorResponse.of(status.value(), LocalDateTime.now(), String.join(", ", errorMessages));

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> emailAlreadyExistsException(EmailAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT; // 409 Conflict
        ErrorResponse errorResponse = ErrorResponse.of(status.value(), LocalDateTime.now(), e.getMessage());

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}
