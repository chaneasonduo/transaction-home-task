package com.example.transaction.exception;

public class TransactionDuplicateException extends TransactionException {
    public TransactionDuplicateException(String id) {
        super("TRANSACTION_DUPLICATE", "交易已存在: " + id);
    }
}