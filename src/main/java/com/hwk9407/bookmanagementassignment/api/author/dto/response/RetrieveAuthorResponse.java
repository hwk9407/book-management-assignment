package com.hwk9407.bookmanagementassignment.api.author.dto.response;

import com.hwk9407.bookmanagementassignment.domain.author.Author;

public record RetrieveAuthorResponse(
        Long id,
        String name,
        String email
) {

    public static RetrieveAuthorResponse from(Author author) {
        return new RetrieveAuthorResponse(
                author.getId(),
                author.getName(),
                author.getEmail()
        );
    }
}
