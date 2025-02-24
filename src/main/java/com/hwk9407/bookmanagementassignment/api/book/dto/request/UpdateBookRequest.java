package com.hwk9407.bookmanagementassignment.api.book.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UpdateBookRequest(

        @Pattern(regexp = "^(?!\\s*$).+", message = "빈 문자열이나 공백으로만 작성할 수 없습니다")
        String title,

        String description,
        String isbn,

        @PastOrPresent
        @JsonAlias({"publication_date", "publicationDate"})
        LocalDate publicationDate,

        @Positive
        @JsonAlias({"author_id", "authorId"})
        Long authorId
) {
}
