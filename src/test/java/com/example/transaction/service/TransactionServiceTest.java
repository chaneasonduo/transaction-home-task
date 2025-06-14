package com.example.transaction.service;

import com.example.transaction.dao.TransactionDao;
import com.example.transaction.exception.TransactionNotFoundException;
import com.example.transaction.model.Transaction;
import com.example.transaction.model.TransactionType;
import com.example.transaction.request.TransactionRequest;
import com.example.transaction.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionDao transactionDao;
    private TransactionRequest testRequest;

    @BeforeEach
    void setUp() {
        transactionDao = org.mockito.Mockito.mock(TransactionDao.class);
        transactionService = new TransactionServiceImpl(transactionDao);
        testRequest = TestUtils.createTransactionRequest(TransactionType.TRANSFER);
    }

    @Test
    void createTransaction_whenValidRequest_thenSuccess() {
        // Given
        TransactionRequest request = TestUtils.createTransactionRequest(TransactionType.TRANSFER);
        // 模拟DAO行为
        when(transactionDao.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Transaction created = transactionService.createTransaction(request);

        // Then
        assertNotNull(created.getId());
        assertEquals(request.getAmount(), created.getAmount());
        assertEquals(request.getDescription(), created.getDescription());
        assertEquals(request.getType(), created.getType());
        assertNotNull(created.getTimestamp());
        // 验证DAO被调用
        verify(transactionDao, times(1)).save(any(Transaction.class));
    }

    @Test
    void getTransactions_whenPaging_thenPageResult() {
        // Given
        Transaction t1 = transactionService.createTransaction(testRequest);
        Transaction t2 = transactionService.createTransaction(testRequest);

        when(transactionDao.findPage(anyInt(), anyInt())).thenReturn(Arrays.asList(t1, t2));

        PageRequest pageRequest = PageRequest.of(0, 1);

        // When
        Page<Transaction> page = transactionService.getTransactions(pageRequest);

        // Then
        assertEquals(1, page.getSize());
        assertEquals(2, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
    }

    @Test
    void getTransaction_whenIdExists_thenReturnTransaction() {
        // Given
        Transaction created = transactionService.createTransaction(testRequest);
        when(transactionDao.findById(created.getId())).thenReturn(created);

        // When
        Transaction retrieved = transactionService.getTransaction(created.getId());

        // Then
        assertNotNull(retrieved);
        assertEquals(created, retrieved);
    }

    @Test
    void getTransaction_whenIdNotExists_thenThrowException() {
        // When & Then
        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.getTransaction("non-existent"));
    }

    @Test
    void updateTransaction_whenValidRequest_thenSuccess() {
        Transaction created = transactionService.createTransaction(testRequest);
        TransactionRequest updateRequest = TestUtils.createTransactionRequest(TransactionType.TRANSFER);
        updateRequest.setDescription("Updated description");
        updateRequest.setAmount(new BigDecimal("200.00")); // 使用字符串构造器

        // mock findById 返回 created
        when(transactionDao.findById(created.getId())).thenReturn(created);

        Transaction updated = transactionService.updateTransaction(created.getId(), updateRequest);

        assertEquals(created.getId(), updated.getId());
        assertEquals("Updated description", updated.getDescription());
        assertEquals(new BigDecimal("200.00"), updated.getAmount()); // 使用 BigDecimal 对象比较
    }

    @Test
    void updateTransaction_whenIdNotExists_thenThrowException() {
        // When & Then
        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.updateTransaction("non-existent", testRequest));
    }

    @Test
    void deleteTransaction_whenIdExists_thenSuccess() {
        // Given
        Transaction created = transactionService.createTransaction(testRequest);
        // mock 删除前能查到，删除后查不到
        when(transactionDao.findById(created.getId()))
                .thenReturn(created) // 第一次调用（delete前）
                .thenReturn(null);   // 第二次调用（delete后）

        // When
        transactionService.deleteTransaction(created.getId());

        // Then
        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.getTransaction(created.getId()));
        // 验证DAO的deleteById被调用
        verify(transactionDao).deleteById(created.getId());
    }
}
