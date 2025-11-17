package com.smartbudget.service.impl;

import com.smartbudget.dto.SummaryDto;
import com.smartbudget.dto.TransactionDto;
import com.smartbudget.entity.Category;
import com.smartbudget.entity.Transaction;
import com.smartbudget.entity.TransactionType;
import com.smartbudget.entity.User;
import com.smartbudget.exception.ResourceNotFoundException;
import com.smartbudget.repository.CategoryRepository;
import com.smartbudget.repository.TransactionRepository;
import com.smartbudget.repository.UserRepository;
import com.smartbudget.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TransactionDto save(TransactionDto transactionDto) {
        User user = userRepository.findById(transactionDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id=" + transactionDto.getUserId()));

        Category category = null;
        if (transactionDto.getCategoryId() != null) {
            category = categoryRepository.findById(transactionDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id=" + transactionDto.getCategoryId()));
        }

        Transaction transaction;
        if (transactionDto.getId() != null) {
            transaction = transactionRepository.findById(transactionDto.getId())
                    .orElse(new Transaction());
        } else {
            transaction = new Transaction();
        }

        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setNotes(transactionDto.getNotes());
        transaction.setTransactionDate(transactionDto.getTransactionDate());

        Transaction saved = transactionRepository.save(transaction);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionDto findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id=" + id));
        return toDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> findAllByUserId(Long userId) {
        return transactionRepository.findByUserIdAndIsDeletedFalse(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> findByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end) {
        return transactionRepository.findByUserIdAndDateRange(userId, start, end, org.springframework.data.domain.Pageable.unpaged())
            .getContent()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id=" + transactionId));
        transaction.setIsDeleted(true);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public SummaryDto getSummary(Long userId, LocalDate start, LocalDate end) {
        BigDecimal totalIncome = transactionRepository.sumIncomeByUserAndDateRange(userId, start, end);
        BigDecimal totalExpense = transactionRepository.sumExpenseByUserAndDateRange(userId, start, end);
        BigDecimal net = totalIncome.subtract(totalExpense);

        Map<String, BigDecimal> breakdown = new HashMap<>();
        // build breakdown by category names
        List<Transaction> transactions = transactionRepository.findByUserIdAndDateRange(userId, start, end, org.springframework.data.domain.Pageable.unpaged())
            .getContent();

        Map<String, BigDecimal> map = transactions.stream()
                .filter(t -> t.getCategory() != null)
                .collect(Collectors.groupingBy(t -> t.getCategory().getName(), Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        breakdown.putAll(map);

        SummaryDto dto = new SummaryDto();
        dto.setUserId(userId);
        dto.setPeriodStart(start);
        dto.setPeriodEnd(end);
        dto.setTotalIncome(totalIncome);
        dto.setTotalExpense(totalExpense);
        dto.setNetBalance(net);
        dto.setCategoryBreakdown(breakdown);
        return dto;
    }

    private TransactionDto toDto(Transaction t) {
        TransactionDto dto = new TransactionDto();
        dto.setId(t.getId());
        dto.setUserId(t.getUser().getId());
        dto.setCategoryId(t.getCategory() != null ? t.getCategory().getId() : null);
        dto.setAmount(t.getAmount());
        dto.setType(t.getType());
        dto.setDescription(t.getDescription());
        dto.setNotes(t.getNotes());
        dto.setTransactionDate(t.getTransactionDate());
        return dto;
    }
}
