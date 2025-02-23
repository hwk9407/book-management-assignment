package com.hwk9407.bookmanagementassignment.api.author.dto.response;

import com.hwk9407.bookmanagementassignment.domain.author.Author;

import java.util.List;

public record RetrieveAllAuthorsResponse(
        List<RetrieveAuthorResponse> contents
) {

    public static RetrieveAllAuthorsResponse from(List<Author> authors) {
        return new RetrieveAllAuthorsResponse(
                authors.stream()
                        .map(RetrieveAuthorResponse::from)
                        .toList()
        );
    }
}
