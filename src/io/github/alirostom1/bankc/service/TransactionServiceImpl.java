package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.alirostom1.bankc.model.dto.TransactionRequestDto;
import io.github.alirostom1.bankc.model.dto.TransactionResponseDto;
import io.github.alirostom1.bankc.model.dto.TransferRequestDto;
import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.model.entity.CheckingAccount;
import io.github.alirostom1.bankc.model.entity.Transaction;
import io.github.alirostom1.bankc.model.enums.TransactionType;
import io.github.alirostom1.bankc.model.mapper.TransactionMapper;
import io.github.alirostom1.bankc.repository.Interface.AccountRepository;
import io.github.alirostom1.bankc.repository.Interface.TransactionRepository;
import io.github.alirostom1.bankc.service.Interface.TransactionService;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void recordTransaction(TransactionRequestDto dto){
        try{
            Optional<Account> account = accountRepository.findByNumber(dto.accountNumber());
            if(account.isEmpty()){
                throw new RuntimeException("Account not found with number: " + dto.accountNumber());
            }
            Account acc = account.get();
            Transaction transaction = TransactionMapper.dtoToTransaction(dto,acc.getId());
            if(dto.type().equals("deposit")){
                acc.setBalance(acc.getBalance() + dto.amount());
                accountRepository.update(acc);
            }else if(dto.type().equals("withdrawal")){
                if(acc instanceof CheckingAccount){
                    CheckingAccount checkAcc = (CheckingAccount) acc;
                    if(acc.getBalance() + checkAcc.getOverdraftLimit() >= dto.amount()){
                        acc.setBalance(acc.getBalance() - dto.amount());
                        accountRepository.update(acc);
                    }else{
                        throw new RuntimeException("Insufficient funds !");
                    }
                }else{
                    if(acc.getBalance() >= dto.amount()){
                        acc.setBalance(acc.getBalance() - dto.amount());
                        accountRepository.update(acc);
                    }else{
                        throw new RuntimeException("Insufficient funds !");
                    }
                }
            }else{

            }
            transactionRepository.save(transaction);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    public void recordTransfer(TransferRequestDto dto)throws SQLException{
        try{
            Optional<Account> reciptioner = accountRepository.findByNumber(dto.recipientNumber());
            Optional<Account> sender = accountRepository.findByNumber(dto.senderNumber());
            if(!reciptioner.isPresent()) throw new RuntimeException("Recipient Not found");
            if(!sender.isPresent()) throw new RuntimeException("Sender Not found");
            Account rec = reciptioner.get();
            Account send = sender.get();
            if(send.getBalance() >= dto.amount()){
                send.setBalance(send.getBalance() - dto.amount());
                accountRepository.update(send);
            }else{
                throw new RuntimeException("Insufficent funds !");
            }
            rec.setBalance(rec.getBalance() + dto.amount());
            accountRepository.update(rec);
            List<Transaction> txs = TransactionMapper.dtoToTransaction(dto, rec.getId(), send.getId());
            for(Transaction tx : txs){
                transactionRepository.save(tx);
            }

        }catch(SQLException e){
            throw e;
        }
    }
    @Override
    public List<TransactionResponseDto> getTransactionsByAccount(String number) {
        try{
            Optional<Account> account = accountRepository.findByNumber(number);
            if(account.isEmpty()){
                throw new RuntimeException("Account not found !");
            }
            List<Transaction> txs = transactionRepository.findByAccountId(account.get().getId());
            return txs.stream().map(TransactionMapper::transactionToDto).collect(Collectors.toList());
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByClient(String clientId) {
        try{
            List<Transaction> txs = transactionRepository.findByClientID(clientId);
            return txs.stream().map(TransactionMapper::transactionToDto).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<TransactionResponseDto> filterTransactionsByAmount(double minAmount) {
        try{
            List<Transaction> txs = transactionRepository.findByAmountRange(minAmount, 10000000);
            return txs.stream().map(TransactionMapper::transactionToDto).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<TransactionResponseDto> filterTransactionsByType(TransactionType type) {
        try{
            List<Transaction> txs = transactionRepository.findByType(type);
            return txs.stream().map(TransactionMapper::transactionToDto).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<TransactionResponseDto> filterTransactionsByDate(LocalDateTime from, LocalDateTime to) {
        try{
            List<Transaction> txs = transactionRepository.findByDateRange(from, to);
            return txs.stream().map(TransactionMapper::transactionToDto).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public List<TransactionResponseDto> filterTransactionsByLocation(String location) {
        try{
            List<Transaction> txs = transactionRepository.findByLocation(location);
            return txs.stream().map(TransactionMapper::transactionToDto).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public Map<TransactionType ,List<TransactionResponseDto>>  groupTransactionsByType(String clientId) {
        try{          
            return transactionRepository.findAll().stream().map(TransactionMapper::transactionToDto).collect(Collectors.groupingBy(TransactionResponseDto::type));
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public Map<String ,List<TransactionResponseDto>>  groupTransactionsByPeriod(String clientId, String period) {
        try{
            return transactionRepository.findByClientID(clientId)
                    .stream().map(TransactionMapper::transactionToDto).collect(Collectors.groupingBy(transaction->{
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
    public List<TransactionResponseDto> detectSuspiciousTransactions(String clientId) {
        try{
            return transactionRepository.findAll().stream().filter((tx) -> tx.amount() > 10000).map(TransactionMapper::transactionToDto).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }
    
}
