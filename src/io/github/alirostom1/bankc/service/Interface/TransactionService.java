package io.github.alirostom1.bankc.service.Interface;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.github.alirostom1.bankc.model.entity.Transaction;
import io.github.alirostom1.bankc.model.enums.TransactionType;

public interface TransactionService {
    void recordTransaction(Transaction transaction);
    List<Transaction> getTransactionsByAccount(String accountId);
    List<Transaction> getTransactionsByClient(String clientId);
    List<Transaction> filterTransactionsByAmount(double minAmount);
    List<Transaction> filterTransactionsByType(TransactionType type);
    List<Transaction> filterTransactionsByDate(LocalDateTime from, LocalDateTime to);
    List<Transaction> filterTransactionsByLocation(String location);
    Map<TransactionType ,List<Transaction>> groupTransactionsByType(String clientId);
    Map<String ,List<Transaction>> groupTransactionsByPeriod(String clientId, String period); 
    double getAverageTransactionAmount(String clientId);
    double getTotalTransactionAmount(String clientId);
    List<Transaction> detectSuspiciousTransactions(String clientId);
}
