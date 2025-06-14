package com.example.transaction.dao;

import com.example.transaction.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Transaction findById(String id) {
        return transactions.get(id);
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    @Override
    public List<Transaction> findPage(int offset, int pageSize) {
        List<Transaction> all = new ArrayList<>(transactions.values());
        int start = Math.min(offset, all.size());
        int end = Math.min(offset + pageSize, all.size());
        return all.subList(start, end);
    }

    @Override
    public void deleteById(String id) {
        transactions.remove(id);
    }

    @Override
    public void update(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }
}
