package com.hwk9407.bookmanagementassignment.api.book.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UpdateBookRequest(

        @Schema(description = "도서 제목", example = "클린 코드")
        @Pattern(regexp = "^(?!\\s*$).+", message = "빈 문자열이나 공백으로만 작성할 수 없습니다")
        String title,

        @Schema(description = "도서 설명 (선택 사항)", example = "애자일 소프트웨어의 혁명적인 패러다임을 제시하는 책")
        String description,

        @Schema(description = "도서의 ISBN 번호 (고유 값)", example = "1002003000")
        String isbn,

        @Schema(description = "출판일 (과거 또는 현재 날짜만 가능)", example = "2025-01-01")
        @PastOrPresent
        @JsonAlias({"publication_date", "publicationDate"})
        LocalDate publicationDate,

        @Schema(description = "저자의 ID (양의 정수만 가능)", example = "1")
        @Positive
        @JsonAlias({"author_id", "authorId"})
        Long authorId
) {
}
