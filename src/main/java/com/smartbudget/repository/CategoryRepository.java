package com.smartbudget.repository;

import com.smartbudget.entity.Category;
import com.smartbudget.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Category entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find all categories for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of categories belonging to the user
     */
    List<Category> findByUserId(Long userId);

    /**
     * Find all categories of a specific type (INCOME or EXPENSE) for a user.
     *
     * @param userId the ID of the user
     * @param type   the transaction type (INCOME or EXPENSE)
     * @return a list of categories of the specified type
     */
    List<Category> findByUserIdAndType(Long userId, TransactionType type);

    /**
     * Find a category by user ID and category name.
     *
     * @param userId the ID of the user
     * @param name   the category name
     * @return an Optional containing the category if found
     */
    Optional<Category> findByUserIdAndName(Long userId, String name);

    /**
     * Find all default categories of a specific type.
     *
     * @param type the transaction type (INCOME or EXPENSE)
     * @return a list of default categories of the specified type
     */
    List<Category> findByIsDefaultTrueAndType(TransactionType type);

    /**
     * Check if a category name exists for a specific user.
     *
     * @param userId the ID of the user
     * @param name   the category name
     * @return true if category exists, false otherwise
     */
    boolean existsByUserIdAndName(Long userId, String name);
}
