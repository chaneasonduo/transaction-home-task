package com.example.transaction.dao;

import com.example.transaction.model.Transaction;
import java.util.List;

public interface TransactionDao {
    java.util.List<Transaction> findPage(int offset, int pageSize);

    Transaction save(Transaction transaction);
    
    Transaction findById(String id);

    List<Transaction> findAll();

    void deleteById(String id);
    
    void update(Transaction transaction);
}
