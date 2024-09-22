package com.finance.category.dto;

import java.time.LocalDateTime;

public record DeleteCategoryResponseDto(
        String message, LocalDateTime deletedAt
) {
}
