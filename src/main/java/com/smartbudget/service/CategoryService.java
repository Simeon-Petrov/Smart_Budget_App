package com.smartbudget.service;

import com.smartbudget.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto findById(Long categoryId);

    List<CategoryDto> findAllByUserId(Long userId);

    void delete(Long categoryId);
}
