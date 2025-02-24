package com.hwk9407.bookmanagementassignment.api.book.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RetrieveAllBooksRequest(

        @Positive(message = "자연수만 입력할 수 있습니다")
        Integer page,

        @Min(1) @Max(50)
        Integer size,

        @Pattern(regexp = "ASC|DESC", message = "orderBy 필드는 ASC 또는 DESC 만 입력할 수 있습니다")
        String orderBy,

        @Pattern(regexp = "title|publicationDate", message = "sortBy 필드는 'title', 'publicationDate' 만 입력할 수 있습니다")
        String sortBy,

        Long authorId,

        @PastOrPresent
        LocalDate startPubDate,

        @PastOrPresent
        LocalDate endPubDate
) {
}