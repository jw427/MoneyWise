package com.finance.category.dto;

import java.time.LocalDateTime;

public record ModifyCategoryResponseDto(
        String message, LocalDateTime updatedAt
) {
}
