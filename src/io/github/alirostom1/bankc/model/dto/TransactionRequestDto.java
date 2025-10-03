package io.github.alirostom1.bankc.model.dto;


public record TransactionRequestDto(
    double amount,
    String type,
    String location,
    String accountNumber
){}
