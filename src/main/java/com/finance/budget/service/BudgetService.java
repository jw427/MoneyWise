package com.finance.budget.service;

import com.finance.budget.domain.Budget;
import com.finance.budget.dto.CreateBudgetRequestDto;
import com.finance.budget.dto.CreateBudgetResponseDto;
import com.finance.budget.repository.BudgetRepository;
import com.finance.category.domain.Category;
import com.finance.category.repository.CategoryRepository;
import com.finance.exception.ConflictException;
import com.finance.exception.ErrorCode;
import com.finance.exception.NotFoundException;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.User;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 예산 설정
    @Transactional
    public List<CreateBudgetResponseDto> createBudget(String token, List<CreateBudgetRequestDto> requestDtoList) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // responseDto 생성
        List<CreateBudgetResponseDto> responseDto = new ArrayList<>();
        for(CreateBudgetRequestDto requestDto : requestDtoList) {
            // categoryId로 category 찾기
            Category category = categoryRepository.findByCategoryId(requestDto.categoryId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
            // 이미 입력한 카테고리의 예산이 설정된 경우
            if(budgetRepository.findByCategory_CategoryIdAndUser_UserId(user.getUserId(), requestDto.categoryId()).isPresent())
                throw new ConflictException(ErrorCode.ALREADY_EXIST_BUDGET);
            // 예산 생성
            Budget budget = Budget.builder()
                    .amount(requestDto.amount())
                    .user(user)
                    .category(category)
                    .build();
            // DB 저장
            budgetRepository.save(budget);
            // responseDto에 추가
            responseDto.add(new CreateBudgetResponseDto(category.getCategoryName(), requestDto.amount(), budget.getCreatedAt()));
        }
        // responseDto 반환
        return responseDto;
    }

    // accessToken에서 회원 정보 가져오기
    public User getUserInfo(String token) {
        String accessToken = token.split("Bearer ")[1];
        String account = tokenProvider.getAccount(accessToken);
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
