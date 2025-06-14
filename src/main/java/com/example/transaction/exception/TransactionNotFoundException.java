package com.example.transaction.exception;

public class TransactionNotFoundException extends TransactionException {
    public TransactionNotFoundException(String id) {
        super("TRANSACTION_NOT_FOUND", "Transaction not found with id:" + id);
    }
}