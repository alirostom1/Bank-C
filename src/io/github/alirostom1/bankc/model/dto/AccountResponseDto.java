package io.github.alirostom1.bankc.model.dto;

public sealed interface AccountResponseDto permits CheckingResponseDto, SavingsResponseDto{
    String id();
    String number();
    double balance();
    String clientId();
}
