package com.finance.expense.domain;

import com.finance.category.domain.Category;
import com.finance.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long expenseId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    @Size(max = 200)
    private String memo;

    @Column(nullable = false)
    private LocalDate expensedAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private boolean excludeFromTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Expense modifyExpense(Long amount, String memo, LocalDate expensedAt, boolean excludeFromTotal, Category category) {
        this.amount = amount;
        this.memo = memo;
        this.expensedAt = expensedAt;
        this.excludeFromTotal = excludeFromTotal;
        this.category = category;
        return this;
    }

    public Expense deleteExpense() {
        this.deletedAt = LocalDateTime.now();
        return this;
    }
}
