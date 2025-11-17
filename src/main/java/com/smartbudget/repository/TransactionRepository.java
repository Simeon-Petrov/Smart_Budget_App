package com.smartbudget.repository;

import com.smartbudget.entity.Transaction;
import com.smartbudget.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for Transaction entity.
 * Provides CRUD operations and custom query methods for filtering and aggregation.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find all non-deleted transactions for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of transactions belonging to the user
     */
    List<Transaction> findByUserIdAndIsDeletedFalse(Long userId);

    /**
     * Find paginated transactions for a user within a date range.
     *
     * @param userId      the ID of the user
     * @param startDate   the start date (inclusive)
     * @param endDate     the end date (inclusive)
     * @param pageable    pagination information
     * @return a page of transactions
     */
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.isDeleted = false " +
           "AND t.transactionDate BETWEEN :startDate AND :endDate ORDER BY t.transactionDate DESC")
    Page<Transaction> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    /**
     * Find transactions by user, type, and date range.
     *
     * @param userId    the ID of the user
     * @param type      the transaction type (INCOME or EXPENSE)
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of matching transactions
     */
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.type = :type " +
           "AND t.isDeleted = false AND t.transactionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserIdTypeAndDateRange(
            @Param("userId") Long userId,
            @Param("type") TransactionType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    /**
     * Find transactions by user, category, and date range.
     *
     * @param userId     the ID of the user
     * @param categoryId the ID of the category
     * @param startDate  the start date (inclusive)
     * @param endDate    the end date (inclusive)
     * @return a list of matching transactions
     */
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.category.id = :categoryId " +
           "AND t.isDeleted = false AND t.transactionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserIdCategoryAndDateRange(
            @Param("userId") Long userId,
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    /**
     * Calculate total income for a user within a date range.
     *
     * @param userId    the ID of the user
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return the total income amount
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.id = :userId " +
           "AND t.type = 'INCOME' AND t.isDeleted = false AND t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal sumIncomeByUserAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    /**
     * Calculate total expenses for a user within a date range.
     *
     * @param userId    the ID of the user
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return the total expense amount
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.id = :userId " +
           "AND t.type = 'EXPENSE' AND t.isDeleted = false AND t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal sumExpenseByUserAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    /**
     * Find all transactions for a specific category.
     *
     * @param categoryId the ID of the category
     * @return a list of transactions using that category
     */
    List<Transaction> findByCategoryId(Long categoryId);

    /**
     * Count non-deleted transactions for a user.
     *
     * @param userId the ID of the user
     * @return the count of transactions
     */
    long countByUserIdAndIsDeletedFalse(Long userId);
}
