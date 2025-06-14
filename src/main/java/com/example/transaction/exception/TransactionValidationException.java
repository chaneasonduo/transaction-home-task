package com.example.transaction.exception;

public class TransactionValidationException extends TransactionException {
    public TransactionValidationException(String message) {
        super("TRANSACTION_VALIDATION_ERROR", message);
    }
}