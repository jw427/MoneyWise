package com.finance.budget.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecommendBudgetRequestDto(
        @NotNull(message = "예산 총 금액을 입력해주세요.") @Positive(message = "1원부터 입력 가능합니다.") Long totalAmount
) {
}
