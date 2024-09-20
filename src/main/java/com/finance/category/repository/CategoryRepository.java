package com.finance.category.repository;

import com.finance.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 회원 식별값과 카테고리명으로 카테고리 조회
    @Query("SELECT c FROM Category c WHERE (c.user IS NULL OR c.user.userId = :userId) AND c.categoryName = :categoryName AND c.deletedAt IS NULL")
    Optional<Category> findByUser_UserIdAndCategoryName(@Param("userId") UUID userId, @Param("categoryName") String categoryName);
}
