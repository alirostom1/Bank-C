package io.github.alirostom1.bankc.model.entity;

import java.time.LocalDateTime;

import io.github.alirostom1.bankc.model.enums.TransactionType;

public record Transaction(
    String id,
    LocalDateTime date,
    double amount,
    TransactionType type,
    String location,
    String accountId
) {
}
