package com.finance.budget.controller;

import com.finance.budget.dto.BudgetListResponseDto;
import com.finance.budget.dto.CreateBudgetRequestDto;
import com.finance.budget.dto.CreateBudgetResponseDto;
import com.finance.budget.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    // 예산 설정
    @PostMapping
    public ResponseEntity<List<CreateBudgetResponseDto>> createBudget(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody List<CreateBudgetRequestDto> requestDto) {
        List<CreateBudgetResponseDto> responseDto = budgetService.createBudget(token, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 예산 전체 조회
    @GetMapping
    public ResponseEntity<List<BudgetListResponseDto>> getBudgetList(@RequestHeader(value = "Authorization") String token) {
        List<BudgetListResponseDto> responseDto = budgetService.getBudgetList(token);
        return ResponseEntity.ok().body(responseDto);
    }
}
