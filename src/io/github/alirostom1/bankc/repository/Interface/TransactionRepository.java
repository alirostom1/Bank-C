package io.github.alirostom1.bankc.repository.Interface;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import io.github.alirostom1.bankc.model.entity.Transaction;
import io.github.alirostom1.bankc.model.enums.TransactionType;

public interface TransactionRepository extends BaseRepository<Transaction> {
    List<Transaction> findByAccountId(String accountId) throws SQLException;
    List<Transaction> findByClientID(String clientId) throws SQLException;
    List<Transaction> findByType(TransactionType type) throws SQLException;
    List<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
    List<Transaction> findByAmountRange(double minAmount, double maxAmount) throws SQLException;
    List<Transaction> findByLocation(String location) throws SQLException;
    void deleteByAccountId(String accountId) throws SQLException;
}
