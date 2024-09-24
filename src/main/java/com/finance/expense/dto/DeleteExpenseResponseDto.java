package com.finance.expense.dto;

import java.time.LocalDateTime;

public record DeleteExpenseResponseDto(
        String message, LocalDateTime deletedAt
) {
}
