package com.hwk9407.bookmanagementassignment.api.book.validator;

import com.hwk9407.bookmanagementassignment.exception.InvalidIsbnException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsbnValidatorTest {

    private final IsbnValidator isbnValidator = new IsbnValidator();

    @Test
    @DisplayName("ISBN은 반드시 10자리 숫자로만 이루어져야 하는 실패 테스트")
    void isbnNumericTest() {
        // given
        String invalidIsbn = "12AB56789C";

        // when
        InvalidIsbnException exception = assertThrows(InvalidIsbnException.class,
                () -> isbnValidator.validate(invalidIsbn));

        // then
        assertEquals("ISBN-10은 10개의 숫자로 이루어져야 합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("ISBN은 정확히 10자리여야 하는 실패 테스트1")
    void shortIsbnTest() {
        // given
        String shortIsbn = "123456789";

        // when
        InvalidIsbnException exceptionShort = assertThrows(InvalidIsbnException.class,
                () -> isbnValidator.validate(shortIsbn));

        // then
        assertEquals("ISBN-10은 10개의 숫자로 이루어져야 합니다.", exceptionShort.getMessage());
    }

    @Test
    @DisplayName("ISBN은 정확히 10자리여야 하는 실패 테스트2")
    void longIsbnTest() {
        // given
        String longIsbn = "12345678900";

        // when
        InvalidIsbnException exceptionLong = assertThrows(InvalidIsbnException.class,
                () -> isbnValidator.validate(longIsbn));

        // then
        assertEquals("ISBN-10은 10개의 숫자로 이루어져야 합니다.", exceptionLong.getMessage());
    }

    @Test
    @DisplayName("ISBN은 0으로 끝나야하는 실패 테스트")
    void isbnZeroEndTest() {
        // given
        String invalidIsbn = "1234567891";

        // when
        InvalidIsbnException exceptionLong = assertThrows(InvalidIsbnException.class,
                () -> isbnValidator.validate(invalidIsbn));

        // then
        assertEquals("ISBN-10의 끝자리는 0으로 끝나야 합니다.", exceptionLong.getMessage());
    }

    @Test
    @DisplayName("ISBN의 국가 코드가 100보다 작으면 실패 테스트")
    void countryCodeLessThan100Test() {
        // given
        String invalidIsbn = "0994567890";

        // when
        InvalidIsbnException exceptionLong = assertThrows(InvalidIsbnException.class,
                () -> isbnValidator.validate(invalidIsbn));

        // then
        assertEquals("ISBN-10의 국가, 언어 식별 번호는 100 ~ 900 사이의 수여야 합니다.", exceptionLong.getMessage());
    }

    @Test
    @DisplayName("ISBN의 국가 코드가 900보다 크면 실패 테스트")
    void countryCodeGreaterThan900Test() {
        // given
        String invalidIsbn = "9014567890";

        // when
        InvalidIsbnException exceptionLong = assertThrows(InvalidIsbnException.class,
                () -> isbnValidator.validate(invalidIsbn));

        // then
        assertEquals("ISBN-10의 국가, 언어 식별 번호는 100 ~ 900 사이의 수여야 합니다.", exceptionLong.getMessage());
    }

    @Test
    @DisplayName("ISBN의 성공 테스트")
    void isbnSuccessTest() {
        // given
        String validIsbn = "1004567890";

        // when & then
        assertDoesNotThrow(() -> isbnValidator.validate(validIsbn));
    }
}