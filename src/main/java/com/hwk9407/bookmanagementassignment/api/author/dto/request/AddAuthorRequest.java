package com.hwk9407.bookmanagementassignment.api.author.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddAuthorRequest(

        @NotBlank
        String name,

        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "올바른 이메일 형식을 입력해주세요."
        )
        String email
) {
}
