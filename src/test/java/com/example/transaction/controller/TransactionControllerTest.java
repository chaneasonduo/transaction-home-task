package com.example.transaction.controller;


import com.example.transaction.exception.TransactionNotFoundException;
import com.example.transaction.model.Transaction;
import com.example.transaction.request.TransactionRequest;
import com.example.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    // 辅助方法，构造一个Transaction对象
    private Transaction createTransaction(String id) {
        Transaction t = new Transaction();
        t.setId(id);
        t.setAmount(new BigDecimal("100.00"));
        t.setDescription("Test transaction");
        return t;
    }

    // 辅助方法，构造一个 TransactionRequest 对象
    private TransactionRequest createTransactionRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(new BigDecimal("100.00"));
        request.setDescription("Test transaction request");
        return request;
    }

    @Test
    void testCreateTransaction_Success() throws Exception {
        TransactionRequest request = createTransactionRequest();
        Transaction transaction = createTransaction("1");

        Mockito.when(transactionService.createTransaction(any(TransactionRequest.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("Test transaction"));
    }

    @Test
    void testGetTransactions_Success() throws Exception {
        Transaction t1 = createTransaction("1");
        Transaction t2 = createTransaction("2");

        Page<Transaction> page = new PageImpl<>(List.of(t1, t2), PageRequest.of(0, 10), 2);
        Mockito.when(transactionService.getTransactions(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/transactions")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[1].id").value("2"));
    }

    @Test
    void testGetTransaction_Success() throws Exception {
        Transaction transaction = createTransaction("123");

        Mockito.when(transactionService.getTransaction("123")).thenReturn(transaction);

        mockMvc.perform(get("/api/transactions/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.description").value("Test transaction"));
    }

    @Test
    void testGetTransaction_NotFound() throws Exception {
        Mockito.when(transactionService.getTransaction("999")).thenReturn(null);

        mockMvc.perform(get("/api/transactions/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTransaction_Success() throws Exception {
        TransactionRequest request = createTransactionRequest();
        Transaction updatedTransaction = createTransaction("1");
        updatedTransaction.setDescription("Updated description");

        Mockito.when(transactionService.updateTransaction(eq("1"), any(TransactionRequest.class)))
                .thenReturn(updatedTransaction);

        mockMvc.perform(put("/api/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    void testDeleteTransaction_Success() throws Exception {
        Mockito.doNothing().when(transactionService).deleteTransaction("1");

        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isNoContent());
    }

    // 如果你对异常处理有 @ControllerAdvice，可以模拟它返回对应状态码
    // 这里简单抛异常演示
    @Test
    void testGetTransaction_ThrowsTransactionNotFoundException() throws Exception {
        Mockito.when(transactionService.getTransaction("404")).thenThrow(new TransactionNotFoundException("404"));

        mockMvc.perform(get("/api/transactions/404"))
                .andExpect(status().isNotFound());
    }
}