package com.hwk9407.bookmanagementassignment.api.book.dto.response;

import java.time.LocalDate;

public record RetrieveAllBooksResponse(
        String title,
        String description,
        String isbn,
        LocalDate publicationDate,
        Long authorId
) {
}
