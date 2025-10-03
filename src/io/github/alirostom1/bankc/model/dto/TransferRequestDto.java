package io.github.alirostom1.bankc.model.dto;

public record TransferRequestDto(
    double amount,
    String location,
    String recipientNumber,
    String senderNumber
) {
    
}
