package com.finance.budget.dto;

import java.time.LocalDateTime;

public record DeleteBudgetResponseDto(
        String message, LocalDateTime deletedAt
) {
}
