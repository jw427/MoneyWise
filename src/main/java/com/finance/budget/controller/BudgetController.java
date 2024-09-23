package com.finance.budget.controller;

import com.finance.budget.dto.*;
import com.finance.budget.service.BudgetService;
import com.finance.category.dto.ModifyBudgetResponseDto;
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

    // 예산 수정
    @PatchMapping("/{budgetId}")
    public ResponseEntity<ModifyBudgetResponseDto> modifyBudget(@PathVariable Long budgetId, @RequestHeader(value = "Authorization") String token, @Valid @RequestBody ModifyBudgetRequestDto requestDto) {
        ModifyBudgetResponseDto responseDto = budgetService.modifyBudget(budgetId, token, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    // 예산 삭제
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<DeleteBudgetResponseDto> deleteBudget(@PathVariable Long budgetId, @RequestHeader(value = "Authorization") String token) {
        DeleteBudgetResponseDto responseDto = budgetService.deleteBudget(budgetId, token);
        return ResponseEntity.ok().body(responseDto);
    }
}
