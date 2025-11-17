package com.smartbudget.service.impl;

import com.smartbudget.dto.CategoryDto;
import com.smartbudget.entity.Category;
import com.smartbudget.entity.User;
import com.smartbudget.exception.ResourceNotFoundException;
import com.smartbudget.repository.CategoryRepository;
import com.smartbudget.repository.UserRepository;
import com.smartbudget.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        User user = userRepository.findById(categoryDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id=" + categoryDto.getUserId()));

        Category category = new Category();
        if (categoryDto.getId() != null) {
            category = categoryRepository.findById(categoryDto.getId())
                    .orElse(new Category());
        }

        category.setUser(user);
        category.setName(categoryDto.getName());
        category.setType(categoryDto.getType());
        category.setColor(categoryDto.getColor());
        category.setDescription(categoryDto.getDescription());
        category.setIsDefault(categoryDto.getIsDefault());

        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id=" + categoryId));
        return toDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAllByUserId(Long userId) {
        return categoryRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id=" + categoryId);
        }
        // Consider reassigning transactions or blocking delete if in use
        categoryRepository.deleteById(categoryId);
    }

    private CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setUserId(category.getUser().getId());
        dto.setName(category.getName());
        dto.setType(category.getType());
        dto.setColor(category.getColor());
        dto.setDescription(category.getDescription());
        dto.setIsDefault(category.getIsDefault());
        return dto;
    }
}
