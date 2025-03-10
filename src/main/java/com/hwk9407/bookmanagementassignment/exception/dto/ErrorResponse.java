package com.hwk9407.bookmanagementassignment.exception.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
    int statusCode,
    LocalDateTime timestamp,
    String message
) {

  public static ErrorResponse of(int value, LocalDateTime now, String message) {
    return new ErrorResponse(value, now, message);
  }
}