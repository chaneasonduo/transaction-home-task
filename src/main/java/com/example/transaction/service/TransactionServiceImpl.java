package com.example.transaction.service;

import com.example.transaction.exception.TransactionNotFoundException;
import com.example.transaction.model.Transaction;
import com.example.transaction.request.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import com.example.transaction.dao.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    private final AtomicInteger counter = new AtomicInteger(0);
    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setId(String.valueOf(counter.incrementAndGet()));
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setType(request.getType());
        transaction.setTimestamp(LocalDateTime.now());

        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    @Cacheable(value = "transactions")
    public Page<Transaction> getTransactions(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int pageSize = pageable.getPageSize();
        List<Transaction> pageContent = transactionDao.findPage(start, pageSize);
        int total = transactionDao.findAll().size();
        return new PageImpl<>(pageContent, pageable, total);
    }

    @Override
    @Cacheable(value = "transactions", key = "#id")
    public Transaction getTransaction(String id) {
        Transaction transaction = transactionDao.findById(id);
        if(null == transaction){
            throw new TransactionNotFoundException(id);
        }
        return transaction;
    }

    @Override
    @CacheEvict(value = "transactions", key = "#id")
    public Transaction updateTransaction(String id, TransactionRequest request) {
        Transaction existing = transactionDao.findById(id);
        if (existing == null) {
            throw new TransactionNotFoundException(id);
        }
        BeanUtils.copyProperties(request, existing, "id", "timestamp");
        transactionDao.update(existing);
        return existing;
    }

    @Override
    @CacheEvict(value = "transactions", allEntries = true)
    public void deleteTransaction(String id) {
        Transaction transaction = transactionDao.findById(id);
        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction not found with id: " + id);
        }
        transactionDao.deleteById(id);
    }
}
