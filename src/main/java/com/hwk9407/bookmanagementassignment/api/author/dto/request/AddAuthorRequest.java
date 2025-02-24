package com.hwk9407.bookmanagementassignment.api.author.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddAuthorRequest(

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣\\s]{1,30}$",
                message = "이름은 한글 또는 영어만 30글자 내로 입력 가능하며, 빈 문자열이나 공백만 입력할 수 없습니다"
        )
        String name,

        @NotBlank
        @Pattern(
                regexp = "^[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "올바른 이메일 형식을 입력해주세요"
        )
        String email
) {
}
