package com.smartbudget.dto;

import com.smartbudget.entity.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO used to create or update a transaction.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    private Long categoryId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @Size(max = 255)
    private String description;

    private String notes;

    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;
}
