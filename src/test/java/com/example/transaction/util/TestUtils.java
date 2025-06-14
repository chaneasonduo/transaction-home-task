package com.example.transaction.util;

import com.example.transaction.model.Transaction;
import com.example.transaction.request.TransactionRequest;
import com.example.transaction.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestUtils {

    public static TransactionRequest createTransactionRequest(TransactionType type) {
        return TransactionRequest.builder()
                .amount(new BigDecimal(100.0))
                .description("Test transaction")
                .type(type)
                .build();
    }

    public static Transaction createTransaction(String id) {

        Transaction transaction = new Transaction();


        return Transaction.builder()
                .id(id)
                .amount(new BigDecimal(100.0))
                .description("Test transaction")
                .type(TransactionType.TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
