package com.hwk9407.bookmanagementassignment.api.book.validator;

import com.hwk9407.bookmanagementassignment.exception.InvalidIsbnException;
import org.springframework.stereotype.Component;

@Component
public class IsbnValidator {

    public void validate(String isbn) {
        if (!isbn.matches("\\d+") || isbn.length() != 10) {
            throw new InvalidIsbnException("ISBN-10은 10개의 숫자로 이루어져야 합니다.");
        }
        if (isbn.charAt(9) != '0') {
            throw new InvalidIsbnException("ISBN-10의 끝자리는 0으로 끝나야 합니다.");
        }
        int countryCode = Integer.parseInt(isbn.substring(0, 3));
        if (countryCode < 100 || countryCode > 900) {
            throw new InvalidIsbnException("ISBN-10의 국가, 언어 식별 번호는 100 ~ 900 사이의 수여야 합니다.");
        }
    }
}
