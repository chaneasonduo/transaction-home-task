package com.example.transaction.request;

import com.example.transaction.model.TransactionType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TransactionRequest {
    @NotNull(message = "交易金额不能为空")
    @DecimalMin(value = "0.01", message = "交易金额必须大于0.01")
    private BigDecimal amount;

    @NotNull(message = "交易描述不能为空")
    @Size(min = 1, max = 255, message = "交易描述长度必须在1到255个字符之间")
    private String description;

    @NotNull(message = "交易类型不能为空")
    private TransactionType type;

    public TransactionRequest(BigDecimal amount, String description, TransactionType type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public TransactionRequest(){

    }
}