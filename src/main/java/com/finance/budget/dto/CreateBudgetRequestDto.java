package com.finance.budget.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateBudgetRequestDto(
        @NotNull(message = "카테고리는 필수로 지정해야 합니다.") Long categoryId,
        @NotNull(message = "예산 금액은 필수로 지정해야 합니다.") @Positive(message = "입력한 값을 다시 확인해주세요.") Long amount
) {
}
