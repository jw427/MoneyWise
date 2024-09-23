package com.finance.budget.dto;

import java.time.LocalDateTime;

public record ModifyBudgetResponseDto(
        String message, LocalDateTime updatedAt
) {
}
