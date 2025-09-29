package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.alirostom1.bankc.model.entity.Transaction;
import io.github.alirostom1.bankc.model.enums.TransactionType;
import io.github.alirostom1.bankc.repository.Interface.TransactionRepository;
import io.github.alirostom1.bankc.service.Interface.TransactionService;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void recordTransaction(Transaction transaction) {
        try{
            transactionRepository.save(transaction);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountId) {
        try{
            return transactionRepository.findByAccountId(accountId);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<Transaction> getTransactionsByClient(String clientId) {
        try{
            return transactionRepository.findByClientID(clientId);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<Transaction> filterTransactionsByAmount(double minAmount) {
        try{
            return transactionRepository.findByAmountRange(minAmount, 10000000);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<Transaction> filterTransactionsByType(TransactionType type) {
        try{
            return transactionRepository.findByType(type);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<Transaction> filterTransactionsByDate(LocalDateTime from, LocalDateTime to) {
        try{
            return transactionRepository.findByDateRange(from, to);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<Transaction> filterTransactionsByLocation(String location) {
        try{
            return transactionRepository.findByLocation(location);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public Map<TransactionType ,List<Transaction>>  groupTransactionsByType(String clientId) {
        try{
            Map<TransactionType,List<Transaction>> map = new HashMap<>();
            map.put(TransactionType.DEPOSIT,transactionRepository.findByType(TransactionType.DEPOSIT).stream().sorted((s1,s2) -> s1.date().compareTo(s2.date())).toList());                       
            map.put(TransactionType.WITHDRAWAL,transactionRepository.findByType(TransactionType.WITHDRAWAL).stream().sorted((s1,s2) -> s1.date().compareTo(s2.date())).toList());
            map.put(TransactionType.TRANSFER,transactionRepository.findByType(TransactionType.TRANSFER).stream().sorted((s1,s2) -> s1.date().compareTo(s2.date())).toList());
            return map;
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public Map<String ,List<Transaction>>  groupTransactionsByPeriod(String clientId, String period) {
        try{
            return transactionRepository.findByClientID(clientId)
                    .stream().collect(Collectors.groupingBy(transaction->{
                        LocalDateTime date = transaction.date();
                        switch (period.toLowerCase()) {
                            case "day":
                                return date.toString();
                            case "month":
                                return date.getYear() + "-" + date.getMonth();
                            case "year":
                                return String.valueOf(date.getYear());
                            default:
                                throw new IllegalArgumentException("Unsupported period (day,month or year) : " + period);
                        }
                    }));
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public double getAverageTransactionAmount(String clientId) {
        try{
            return transactionRepository.findByClientID(clientId).stream().mapToDouble(Transaction::amount).average().orElse(0.0);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public double getTotalTransactionAmount(String clientId) {
        try{
            return transactionRepository.findByClientID(clientId).stream().mapToDouble(Transaction::amount).sum();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<Transaction> detectSuspiciousTransactions(String clientId) {
        try{
            return transactionRepository.findAll().stream().filter((tx) -> tx.amount() > 10000).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }
    
}
