package com.hwk9407.bookmanagementassignment.api.author.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record UpdateAuthorRequest(

        @Schema(description = "바꿀 저자 이름", example = "이몽룡")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣\\s]{1,30}$",
                message = "이름은 한글 또는 영어만 30글자 내로 입력 가능하며, 빈 문자열이나 공백만 입력할 수 없습니다"
        )
        String name,

        @Schema(description = "바꿀 이메일 (고유 값)", example = "lee@example.com")
        @Pattern(
                regexp = "^[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "올바른 이메일 형식을 입력해주세요"
        )
        String email
) {
}
