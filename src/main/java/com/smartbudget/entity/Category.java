package com.smartbudget.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Category entity representing a transaction category (Income or Expense).
 * Each category belongs to a specific user and can have multiple transactions.
 */
@Entity
@Table(name = "categories", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private TransactionType type; // INCOME or EXPENSE

    @Column(name = "color", length = 7)
    private String color; // Hex color, e.g., #FF5733

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * One-to-many relationship: Category can have multiple transactions.
     * When a category is deleted, transactions' category_id becomes NULL.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    /**
     * PrePersist: Auto-set creation timestamp.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (isDefault == null) {
            isDefault = false;
        }
    }
}
