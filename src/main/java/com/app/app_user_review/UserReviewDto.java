package com.app.app_user_review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserReviewDto(
        Long id,

        String date,
        @Min(value = 0, message = "score is required min 0")
        @Max(value = 10, message = "score is required max 0")
        int score,

        @Size(min = 1, max = 5000, message = "text is required length 1-5000")
        @NotEmpty(message = "text is required")
        String text
) {
}
