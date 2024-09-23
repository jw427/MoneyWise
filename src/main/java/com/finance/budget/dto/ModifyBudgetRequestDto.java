package com.finance.budget.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ModifyBudgetRequestDto(
        @NotNull(message = "수정할 예산 금액을 입력해주세요.") @Positive(message = "1원부터 입력 가능합니다.") Long amount
) {
}
