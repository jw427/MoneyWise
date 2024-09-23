package com.finance.expense.service;

import com.finance.budget.repository.BudgetRepository;
import com.finance.category.domain.Category;
import com.finance.category.repository.CategoryRepository;
import com.finance.exception.BadRequestException;
import com.finance.exception.ErrorCode;
import com.finance.exception.NotFoundException;
import com.finance.expense.domain.Expense;
import com.finance.expense.dto.CreateExpenseRequestDto;
import com.finance.expense.dto.CreateExpenseResponseDto;
import com.finance.expense.repository.ExpenseRepository;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.User;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 지출 생성
    @Transactional
    public CreateExpenseResponseDto createExpense(String token, CreateExpenseRequestDto requestDto) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // categoryId로 category 찾기
        Category category = categoryRepository.findByCategoryId(requestDto.categoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        // 해당 카테고리로 설정된 예산이 없을 경우
        if(budgetRepository.findByCategory_CategoryIdAndUser_UserId(user.getUserId(), requestDto.categoryId()).isEmpty())
            throw new BadRequestException(ErrorCode.BUDGET_NOT_EXIST);
        // 지출 생성
        Expense expense = Expense.builder()
                .amount(requestDto.amount())
                .memo(requestDto.memo())
                .expensedAt(requestDto.expensedAt())
                .excludeFromTotal(requestDto.excludeFromTotal())
                .user(user)
                .category(category)
                .build();
        // DB 저장
        expenseRepository.save(expense);
        // responseDto 반환
        return new CreateExpenseResponseDto(category.getCategoryName(), requestDto.amount(), requestDto.expensedAt(), expense.getCreatedAt(), requestDto.excludeFromTotal());
    }

    // accessToken에서 회원 정보 가져오기
    public User getUserInfo(String token) {
        String accessToken = token.split("Bearer ")[1];
        String account = tokenProvider.getAccount(accessToken);
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
