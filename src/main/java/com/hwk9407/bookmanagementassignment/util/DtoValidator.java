package com.hwk9407.bookmanagementassignment.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class DtoValidator {

    private final Validator validator;

    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(violation -> violation.getPropertyPath() + " 필드는 " + violation.getMessage())
                    .toList();
            String formattedMessage = String.join(", ", errorMessages);
            throw new IllegalArgumentException(formattedMessage);
        }
    }
}
