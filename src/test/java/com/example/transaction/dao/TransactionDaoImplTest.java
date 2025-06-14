package com.example.transaction.dao;

import com.example.transaction.model.Transaction;
import com.example.transaction.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDaoImplTest {

    private TransactionDaoImpl transactionDao;

    @BeforeEach
    void setUp() {
        transactionDao = new TransactionDaoImpl();
    }

    @Test
    void save_and_findById_shouldWork() {
        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setType(TransactionType.TRANSFER);
        transactionDao.save(transaction);
        Transaction found = transactionDao.findById("1");
        assertNotNull(found);
        assertEquals("1", found.getId());
        assertEquals(new BigDecimal("100.00"), found.getAmount());
        assertEquals(TransactionType.TRANSFER, found.getType());
    }

    @Test
    void findAll_shouldReturnAll() {
        Transaction t1 = new Transaction();
        t1.setId("1");
        Transaction t2 = new Transaction();
        t2.setId("2");
        transactionDao.save(t1);
        transactionDao.save(t2);
        List<Transaction> all = transactionDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findPage_shouldReturnPagedResults() {
        for (int i = 1; i <= 5; i++) {
            Transaction t = new Transaction();
            t.setId(String.valueOf(i));
            transactionDao.save(t);
        }
        List<Transaction> page = transactionDao.findPage(1, 2);
        assertEquals(2, page.size());
        assertEquals("2", page.get(0).getId());
        assertEquals("3", page.get(1).getId());
    }

    @Test
    void deleteById_shouldRemoveTransaction() {
        Transaction t = new Transaction();
        t.setId("1");
        transactionDao.save(t);
        transactionDao.deleteById("1");
        assertNull(transactionDao.findById("1"));
    }

    @Test
    void update_shouldReplaceTransaction() {
        Transaction t = new Transaction();
        t.setId("1");
        t.setDescription("old");
        transactionDao.save(t);
        Transaction updated = new Transaction();
        updated.setId("1");
        updated.setDescription("new");
        transactionDao.update(updated);
        Transaction found = transactionDao.findById("1");
        assertEquals("new", found.getDescription());
    }
}
