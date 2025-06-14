package com.example.transaction.model;

public enum TransactionType {

    DEPOSIT("存款"),
    WITHDRAWAL("取款"),
    TRANSFER("转账"),
    PAYMENT("支付"),
    REFUND("退款"),
    LOAN("贷款"),
    INTEREST("利息"),
    FEE("手续费");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
