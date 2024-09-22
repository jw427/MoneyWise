package com.finance.budget.dto;

import java.time.LocalDateTime;

public record CreateBudgetResponseDto(
        String categoryName, Long amount, LocalDateTime createdAt
) {
}
