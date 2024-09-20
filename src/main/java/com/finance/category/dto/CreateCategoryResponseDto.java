package com.finance.category.dto;

import java.time.LocalDateTime;

public record CreateCategoryResponseDto(
        String message, LocalDateTime createdAt
) {
}
