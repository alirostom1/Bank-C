package io.github.alirostom1.bankc.service.Interface;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.github.alirostom1.bankc.model.dto.TransactionRequestDto;
import io.github.alirostom1.bankc.model.dto.TransactionResponseDto;
import io.github.alirostom1.bankc.model.dto.TransferRequestDto;
import io.github.alirostom1.bankc.model.enums.TransactionType;

public interface TransactionService {
    void recordTransaction(TransactionRequestDto transaction);
    void recordTransfer(TransferRequestDto dto) throws SQLException;
    List<TransactionResponseDto> getTransactionsByAccount(String accountId);
    List<TransactionResponseDto> getTransactionsByClient(String clientId);
    List<TransactionResponseDto> filterTransactionsByAmount(double minAmount);
    List<TransactionResponseDto> filterTransactionsByType(TransactionType type);
    List<TransactionResponseDto> filterTransactionsByDate(LocalDateTime from, LocalDateTime to);
    List<TransactionResponseDto> filterTransactionsByLocation(String location);
    Map<TransactionType ,List<TransactionResponseDto>> groupTransactionsByType(String clientId);
    Map<String ,List<TransactionResponseDto>> groupTransactionsByPeriod(String clientId, String period); 
    double getAverageTransactionAmount(String clientId);
    double getTotalTransactionAmount(String clientId);
    List<TransactionResponseDto> detectSuspiciousTransactions(String clientId);
}
