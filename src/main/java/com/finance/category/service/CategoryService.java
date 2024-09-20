package com.finance.category.service;

import com.finance.category.domain.Category;
import com.finance.category.dto.CreateCategoryRequestDto;
import com.finance.category.dto.CreateCategoryResponseDto;
import com.finance.category.repository.CategoryRepository;
import com.finance.exception.ConflictException;
import com.finance.exception.ErrorCode;
import com.finance.exception.NotFoundException;
import com.finance.exception.UnauthorizedException;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.User;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 카테고리 생성
    @Transactional
    public CreateCategoryResponseDto createCategory(String token, CreateCategoryRequestDto requestDto) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // 해당 회원이 만든 카테고리 또는 기본 카테고리에 똑같은 카테고리명이 존재하는 경우 error
        if(categoryRepository.findByUser_UserIdAndCategoryName(user.getUserId(), requestDto.categoryName()).isPresent())
            throw new ConflictException(ErrorCode.ALREADY_EXIST_CATEGORY);
        // 카테고리 생성
        Category category = Category.builder()
                .categoryName(requestDto.categoryName())
                .user(user)
                .build();
        // DB 저장
        categoryRepository.save(category);
        // responseDto 반환
        return new CreateCategoryResponseDto(requestDto.categoryName() + "가 생성되었습니다!", category.getCreatedAt());
    }

    // accessToken에서 회원 정보 가져오기
    public User getUserInfo(String token) {
        String accessToken = token.split("Bearer ")[1];
        String account = tokenProvider.getAccount(accessToken);
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
