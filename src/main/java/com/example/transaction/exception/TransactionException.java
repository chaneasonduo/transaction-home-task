package com.example.transaction.exception;

public class TransactionException extends RuntimeException {
    private final String code;
    private final String message;

    public TransactionException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}