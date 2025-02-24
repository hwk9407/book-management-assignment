package com.hwk9407.bookmanagementassignment.api.author.dto.response;

import com.hwk9407.bookmanagementassignment.domain.author.Author;
import org.springframework.data.domain.Page;

import java.util.List;

public record RetrieveAllAuthorsResponse(
        List<RetrieveAuthorResponse> contents,
        int page,
        int size,
        int totalPage
) {

    public static RetrieveAllAuthorsResponse from(Page<Author> authors) {
        return new RetrieveAllAuthorsResponse(
                authors.getContent().stream()
                        .map(RetrieveAuthorResponse::from)
                        .toList(),
                authors.getNumber() + 1,
                authors.getSize(),
                authors.getTotalPages()

        );
    }
}
