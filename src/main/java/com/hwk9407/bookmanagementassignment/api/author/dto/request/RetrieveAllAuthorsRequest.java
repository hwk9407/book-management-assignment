package com.hwk9407.bookmanagementassignment.api.author.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record RetrieveAllAuthorsRequest(

        @Positive(message = "자연수만 입력할 수 있습니다")
        Integer page,

        @Min(1) @Max(50)
        Integer size
) {
}
