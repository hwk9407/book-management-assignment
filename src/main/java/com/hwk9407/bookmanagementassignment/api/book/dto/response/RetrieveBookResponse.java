package com.hwk9407.bookmanagementassignment.api.book.dto.response;

import com.hwk9407.bookmanagementassignment.domain.book.Book;

import java.time.LocalDate;

public record RetrieveBookResponse(
        Long id,
        String title,
        String description,
        String isbn,
        LocalDate publicationDate,
        Long authorId,
        String authorName
) {

    public static RetrieveBookResponse from(Book book) {
        return new RetrieveBookResponse(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getIsbn(),
                book.getPublicationDate(),
                book.getAuthor().getId(),
                book.getAuthor().getName()
        );
    }
}
