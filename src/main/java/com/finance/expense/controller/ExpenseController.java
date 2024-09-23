package com.finance.expense.controller;

import com.finance.expense.dto.CreateExpenseRequestDto;
import com.finance.expense.dto.CreateExpenseResponseDto;
import com.finance.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    // 지출 생성
    @PostMapping
    public ResponseEntity<CreateExpenseResponseDto> createExpense(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody CreateExpenseRequestDto requestDto) {
        CreateExpenseResponseDto responseDto = expenseService.createExpense(token, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
