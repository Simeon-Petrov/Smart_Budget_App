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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TransactionServiceImpl using JUnit 5 and Mockito.
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private User testUser;
    private Category testCategory;
    private Transaction testTransaction;
    private TransactionDto testTransactionDto;

    @BeforeEach
    public void setUp() {
        // Setup test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashed_password");
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());

        // Setup test category
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setUser(testUser);
        testCategory.setName("Food");
        testCategory.setType(TransactionType.EXPENSE);
        testCategory.setColor("#F39C12");
        testCategory.setIsDefault(false);
        testCategory.setCreatedAt(LocalDateTime.now());

        // Setup test transaction entity
        testTransaction = new Transaction();
        testTransaction.setId(1L);
        testTransaction.setUser(testUser);
        testTransaction.setCategory(testCategory);
        testTransaction.setAmount(new BigDecimal("50.00"));
        testTransaction.setType(TransactionType.EXPENSE);
        testTransaction.setDescription("Grocery shopping");
        testTransaction.setNotes("Weekly groceries");
        testTransaction.setTransactionDate(LocalDate.now());
        testTransaction.setIsDeleted(false);
        testTransaction.setCreatedAt(LocalDateTime.now());
        testTransaction.setUpdatedAt(LocalDateTime.now());

        // Setup test DTO
        testTransactionDto = new TransactionDto();
        testTransactionDto.setId(1L);
        testTransactionDto.setUserId(1L);
        testTransactionDto.setCategoryId(1L);
        testTransactionDto.setAmount(new BigDecimal("50.00"));
        testTransactionDto.setType(TransactionType.EXPENSE);
        testTransactionDto.setDescription("Grocery shopping");
        testTransactionDto.setNotes("Weekly groceries");
        testTransactionDto.setTransactionDate(LocalDate.now());
    }

    /**
     * Test: Successfully save a new transaction.
     * Verify that save() correctly maps DTO to Entity and calls repository.save().
     */
    @Test
    public void testSaveTransaction_success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);

        // Act
        TransactionDto result = transactionService.save(testTransactionDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal("50.00"), result.getAmount());
        assertEquals(TransactionType.EXPENSE, result.getType());
        assertEquals("Grocery shopping", result.getDescription());

        // Verify repository interactions
        verify(userRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    /**
     * Test: Save transaction fails when user doesn't exist.
     * Expect ResourceNotFoundException to be thrown.
     */
    @Test
    public void testSaveTransaction_userNotFound() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        testTransactionDto.setUserId(999L);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.save(testTransactionDto);
        });

        verify(userRepository, times(1)).findById(999L);
        verify(transactionRepository, never()).save(any());
    }

    /**
     * Test: Save transaction fails when category doesn't exist.
     * Expect ResourceNotFoundException to be thrown.
     */
    @Test
    public void testSaveTransaction_categoryNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        testTransactionDto.setCategoryId(999L);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.save(testTransactionDto);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).findById(999L);
        verify(transactionRepository, never()).save(any());
    }

    /**
     * Test: Find transaction by ID returns correct DTO.
     */
    @Test
    public void testFindById_success() {
        // Arrange
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));

        // Act
        TransactionDto result = transactionService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal("50.00"), result.getAmount());
        verify(transactionRepository, times(1)).findById(1L);
    }

    /**
     * Test: Find transaction by non-existent ID throws exception.
     */
    @Test
    public void testFindById_notFound() {
        // Arrange
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.findById(999L);
        });

        verify(transactionRepository, times(1)).findById(999L);
    }

    /**
     * Test: Get summary calculates correct income, expense, and net totals.
     * Verify category breakdown is properly constructed.
     */
    @Test
    public void testCalculateSummary_returnsCorrectTotals() {
        // Arrange
        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();

        BigDecimal totalIncome = new BigDecimal("3000.00");
        BigDecimal totalExpense = new BigDecimal("1500.00");

        when(transactionRepository.sumIncomeByUserAndDateRange(1L, start, end))
                .thenReturn(totalIncome);
        when(transactionRepository.sumExpenseByUserAndDateRange(1L, start, end))
                .thenReturn(totalExpense);

        // Create mock transactions for breakdown
        Transaction incomeTransaction = new Transaction();
        incomeTransaction.setId(2L);
        incomeTransaction.setUser(testUser);
        incomeTransaction.setCategory(new Category() {{ setName("Salary"); }});
        incomeTransaction.setAmount(new BigDecimal("3000.00"));
        incomeTransaction.setType(TransactionType.INCOME);
        incomeTransaction.setIsDeleted(false);

        Transaction expenseTransaction1 = new Transaction();
        expenseTransaction1.setId(3L);
        expenseTransaction1.setUser(testUser);
        expenseTransaction1.setCategory(new Category() {{ setName("Food"); }});
        expenseTransaction1.setAmount(new BigDecimal("500.00"));
        expenseTransaction1.setType(TransactionType.EXPENSE);
        expenseTransaction1.setIsDeleted(false);

        Transaction expenseTransaction2 = new Transaction();
        expenseTransaction2.setId(4L);
        expenseTransaction2.setUser(testUser);
        expenseTransaction2.setCategory(new Category() {{ setName("Rent"); }});
        expenseTransaction2.setAmount(new BigDecimal("1000.00"));
        expenseTransaction2.setType(TransactionType.EXPENSE);
        expenseTransaction2.setIsDeleted(false);

        List<Transaction> transactions = Arrays.asList(incomeTransaction, expenseTransaction1, expenseTransaction2);
        Page<Transaction> transactionPage = new PageImpl<>(transactions);

        when(transactionRepository.findByUserIdAndDateRange(eq(1L), eq(start), eq(end), any(Pageable.class)))
                .thenReturn(transactionPage);

        // Act
        SummaryDto result = transactionService.getSummary(1L, start, end);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(start, result.getPeriodStart());
        assertEquals(end, result.getPeriodEnd());
        assertEquals(totalIncome, result.getTotalIncome());
        assertEquals(totalExpense, result.getTotalExpense());
        assertEquals(new BigDecimal("1500.00"), result.getNetBalance()); // 3000 - 1500

        // Verify category breakdown
        assertNotNull(result.getCategoryBreakdown());
        assertEquals(new BigDecimal("3000.00"), result.getCategoryBreakdown().get("Salary"));
        assertEquals(new BigDecimal("500.00"), result.getCategoryBreakdown().get("Food"));
        assertEquals(new BigDecimal("1000.00"), result.getCategoryBreakdown().get("Rent"));

        verify(transactionRepository, times(1)).sumIncomeByUserAndDateRange(1L, start, end);
        verify(transactionRepository, times(1)).sumExpenseByUserAndDateRange(1L, start, end);
        verify(transactionRepository, times(1)).findByUserIdAndDateRange(1L, start, end, Pageable.unpaged());
    }

    /**
     * Test: Delete (soft-delete) transaction successfully.
     * Verify that delete() marks is_deleted as true via repository save.
     */
    @Test
    public void testDeleteTransaction_success() {
        // Arrange
        Transaction transactionToDelete = new Transaction();
        transactionToDelete.setId(1L);
        transactionToDelete.setUser(testUser);
        transactionToDelete.setIsDeleted(false);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transactionToDelete));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transactionToDelete);

        // Act
        transactionService.delete(1L);

        // Assert
        // Verify that save was called (soft-delete sets isDeleted=true)
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    /**
     * Test: Delete transaction fails when transaction doesn't exist.
     */
    @Test
    public void testDeleteTransaction_notFound() {
        // Arrange
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.delete(999L);
        });

        verify(transactionRepository, times(1)).findById(999L);
        verify(transactionRepository, never()).save(any());
    }

    /**
     * Test: Find all transactions by user ID.
     */
    @Test
    public void testFindAllByUserId_success() {
        // Arrange
        List<Transaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByUserIdAndIsDeletedFalse(1L))
                .thenReturn(transactions);

        // Act
        List<TransactionDto> result = transactionService.findAllByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("50.00"), result.get(0).getAmount());
        verify(transactionRepository, times(1)).findByUserIdAndIsDeletedFalse(1L);
    }
}
