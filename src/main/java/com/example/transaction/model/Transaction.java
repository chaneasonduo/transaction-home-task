package com.example.transaction.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class Transaction {
    @NotNull
    private String id;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    private TransactionType type;

    @NotNull
    private LocalDateTime timestamp;

    public Transaction() {
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(String id, BigDecimal amount, String description, TransactionType type, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.timestamp = timestamp;
    }
}