package com.smartbudget.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * DTO for summary results (aggregations by period).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDto {

    private Long userId;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal netBalance;

    // category name -> amount
    private Map<String, BigDecimal> categoryBreakdown;
}
