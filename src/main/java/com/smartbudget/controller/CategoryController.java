package com.smartbudget.controller;

import com.smartbudget.dto.CategoryDto;
import com.smartbudget.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Category CRUD operations.
 */
@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Create a new category.
     *
     * @param categoryDto the category data
     * @return ResponseEntity with created category and 201 status
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto created = categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get a category by ID.
     *
     * @param id the category ID
     * @return ResponseEntity with category data
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.findById(id);
        return ResponseEntity.ok(categoryDto);
    }

    /**
     * Get all categories for a specific user.
     *
     * @param userId the user ID
     * @return list of categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategoriesByUser(
            @RequestParam Long userId) {
        List<CategoryDto> categories = categoryService.findAllByUserId(userId);
        return ResponseEntity.ok(categories);
    }

    /**
     * Update an existing category.
     *
     * @param id the category ID
     * @param categoryDto the updated category data
     * @return ResponseEntity with updated category
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        CategoryDto updated = categoryService.save(categoryDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a category.
     *
     * @param id the category ID
     * @return ResponseEntity with 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
