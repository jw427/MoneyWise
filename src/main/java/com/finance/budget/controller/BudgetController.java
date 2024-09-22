package com.finance.budget.controller;

import com.finance.budget.dto.CreateBudgetRequestDto;
import com.finance.budget.dto.CreateBudgetResponseDto;
import com.finance.budget.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    // 예산 설정
    @PostMapping
    public ResponseEntity<CreateBudgetResponseDto> createBudget(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody CreateBudgetRequestDto requestDto) {
        CreateBudgetResponseDto responseDto = budgetService.createBudget(token, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
