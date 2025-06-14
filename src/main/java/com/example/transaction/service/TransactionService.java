package com.example.transaction.service;

import com.example.transaction.model.Transaction;
import com.example.transaction.request.TransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Transaction createTransaction(TransactionRequest request);

    Page<Transaction> getTransactions(Pageable pageable);

    Transaction getTransaction(String id);

    Transaction updateTransaction(String id, TransactionRequest request);

    void deleteTransaction(String id);
}