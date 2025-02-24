package com.hwk9407.bookmanagementassignment.util;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.RetrieveAllAuthorsRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DtoValidatorTest {

    @Mock
    private Validator validator;

    @InjectMocks
    private DtoValidator dtoValidator;

    @Test
    @DisplayName("유효한 DTO가 주어지면 예외없이 성공 테스트")
    void passValidationSuccessTest() {
        // given
        RetrieveAllAuthorsRequest req = new RetrieveAllAuthorsRequest(1, 10);
        when(validator.validate(req)).thenReturn(Set.of());

        // when & then
        Assertions.assertThatCode(() -> dtoValidator.validate(req))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("유효하지 않은 DTO가 주어지면 IllegalArgumentException 발생 테스트")
    void throwExceptionWhenInvalidDtoGiven() {
        // given
        RetrieveAllAuthorsRequest invalidReq = new RetrieveAllAuthorsRequest(0, 100); // 유효하지 않은 값
        ConstraintViolation<RetrieveAllAuthorsRequest> violation = mock(ConstraintViolation.class);
        when(validator.validate(invalidReq)).thenReturn(Set.of(violation));

        // when & then
        assertThatThrownBy(() -> dtoValidator.validate(invalidReq))
                .isInstanceOf(IllegalArgumentException.class);
    }
}