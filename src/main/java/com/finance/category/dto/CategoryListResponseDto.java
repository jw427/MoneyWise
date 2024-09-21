package com.finance.category.dto;

import java.util.UUID;

public record CategoryListResponseDto(
        String categoryName, UUID userId
) {
}
