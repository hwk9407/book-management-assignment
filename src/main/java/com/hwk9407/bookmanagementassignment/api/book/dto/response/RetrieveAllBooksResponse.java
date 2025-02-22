package com.hwk9407.bookmanagementassignment.api.book.dto.response;

import com.hwk9407.bookmanagementassignment.domain.book.Book;

import java.util.List;

public record RetrieveAllBooksResponse(
        List<RetrieveBookResponse> contents
) {

    public static RetrieveAllBooksResponse from(List<Book> books) {
        return new RetrieveAllBooksResponse(
                books.stream()
                        .map(RetrieveBookResponse::from)
                        .toList()
        );
    }
}
