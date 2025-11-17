package com.smartbudget.dto;

import com.smartbudget.entity.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for category create/update operations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String name;

    @NotNull(message = "Category type is required")
    private TransactionType type;

    @Size(max = 7, message = "Color should be a hex code e.g. #FF5733")
    private String color;

    private String description;

    private Boolean isDefault = false;
}
