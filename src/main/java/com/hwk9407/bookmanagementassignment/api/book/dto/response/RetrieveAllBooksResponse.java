package com.hwk9407.bookmanagementassignment.api.book.dto.response;

import com.hwk9407.bookmanagementassignment.domain.book.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public record RetrieveAllBooksResponse(
        List<RetrieveBookResponse> contents,
        int page,
        int size,
        int totalPage
) {

    public static RetrieveAllBooksResponse from(Page<Book> books) {
        return new RetrieveAllBooksResponse(
                books.getContent().stream()
                        .map(RetrieveBookResponse::from)
                        .toList(),
                books.getNumber() + 1,
                books.getSize(),
                books.getTotalPages()
        );
    }
}
