package io.github.alirostom1.bankc.model.dto;

import java.time.LocalDateTime;

import io.github.alirostom1.bankc.model.enums.TransactionType;

public record TransactionResponseDto(
    String id,
    LocalDateTime date,
    double amount,
    TransactionType type,
    String location,
    String accountId
){
    @Override
    public String toString() {
        return "Transaction Details :" +
                "   id: " + id + '\n' +
                "   date: " + date + '\n' +
                "   amount: " + amount +
                "   type: " + type.name() + '\n' +
                "   location: " + location + '\n' +
                "   accountId: " + accountId;
    }
}