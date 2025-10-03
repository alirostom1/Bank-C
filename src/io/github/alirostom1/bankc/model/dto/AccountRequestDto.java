package io.github.alirostom1.bankc.model.dto;

public record AccountRequestDto(
    String id,
    String number,
    Double balance,
    String clientId,
    String type,
    double overdraftLimit,
    double interestRate
) 
{}
