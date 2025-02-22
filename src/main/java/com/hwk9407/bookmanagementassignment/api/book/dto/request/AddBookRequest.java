package com.hwk9407.bookmanagementassignment.api.book.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record AddBookRequest(
        @NotBlank
        String title,

        @Nullable
        String description,

        @NotNull
        String isbn,

        @PastOrPresent
        @JsonAlias({"publication_date", "publicationDate"})
        LocalDate publicationDate,

        @NotNull @Positive
        @JsonAlias({"author_id", "authorId"})
        Long authorId
) {
}