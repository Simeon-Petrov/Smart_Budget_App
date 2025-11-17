package com.smartbudget.controller;

import com.smartbudget.dto.SummaryDto;
import com.smartbudget.dto.TransactionDto;
import com.smartbudget.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Transaction CRUD operations and summaries.
 */
@RestController
@RequestMapping("/api/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Create a new transaction.
     *
     * @param transactionDto the transaction data
     * @return ResponseEntity with created transaction and 201 status
     */
    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(
            @Valid @RequestBody TransactionDto transactionDto) {
        TransactionDto created = transactionService.save(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get a transaction by ID.
     *
     * @param id the transaction ID
     * @return ResponseEntity with transaction data
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Long id) {
        TransactionDto transactionDto = transactionService.findById(id);
        return ResponseEntity.ok(transactionDto);
    }

    /**
     * Get all transactions for the authenticated user.
     *
     * @param userId the user ID (passed as query param or from auth context)
     * @return list of transactions
     */
    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions(
            @RequestParam Long userId) {
        List<TransactionDto> transactions = transactionService.findAllByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get transactions for a specific date range.
     *
     * @param userId the user ID
     * @param startDate the start date (format: yyyy-MM-dd)
     * @param endDate the end date (format: yyyy-MM-dd)
     * @return list of transactions within the range
     */
    @GetMapping("/range")
    public ResponseEntity<List<TransactionDto>> getTransactionsByDateRange(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TransactionDto> transactions = transactionService.findByUserIdAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Get summary (totals and breakdown) for a user within a date range.
     *
     * @param userId the user ID
     * @param startDate the start date (format: yyyy-MM-dd)
     * @param endDate the end date (format: yyyy-MM-dd)
     * @return SummaryDto with aggregated data
     */
    @GetMapping("/summary")
    public ResponseEntity<SummaryDto> getSummary(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        SummaryDto summary = transactionService.getSummary(userId, startDate, endDate);
        return ResponseEntity.ok(summary);
    }

    /**
     * Update an existing transaction.
     *
     * @param id the transaction ID
     * @param transactionDto the updated transaction data
     * @return ResponseEntity with updated transaction
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDto transactionDto) {
        transactionDto.setId(id);
        TransactionDto updated = transactionService.save(transactionDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete (soft-delete) a transaction.
     *
     * @param id the transaction ID
     * @return ResponseEntity with 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
