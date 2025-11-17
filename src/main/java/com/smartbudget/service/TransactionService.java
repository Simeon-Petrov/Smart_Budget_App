package com.smartbudget.service;

import com.smartbudget.dto.SummaryDto;
import com.smartbudget.dto.TransactionDto;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    TransactionDto save(TransactionDto transactionDto);

    TransactionDto findById(Long id);

    List<TransactionDto> findAllByUserId(Long userId);

    List<TransactionDto> findByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end);

    void delete(Long transactionId);

    SummaryDto getSummary(Long userId, LocalDate start, LocalDate end);
}
