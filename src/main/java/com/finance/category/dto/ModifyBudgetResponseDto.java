package com.finance.category.dto;

import java.time.LocalDateTime;

public record ModifyBudgetResponseDto(
        String message, LocalDateTime updatedAt
) {
}
