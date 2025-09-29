package io.github.alirostom1.bankc.model.entity;

import io.github.alirostom1.bankc.model.enums.TransactionType;

public record Transaction(
    String id,
    String date,
    double amount,
    TransactionType type,
    String location,
    String accountId
) {
}
