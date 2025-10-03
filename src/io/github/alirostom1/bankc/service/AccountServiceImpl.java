package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.dto.AccountRequestDto;
import io.github.alirostom1.bankc.model.dto.AccountResponseDto;
import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.model.mapper.AccountMapper;
import io.github.alirostom1.bankc.repository.Interface.AccountRepository;
import io.github.alirostom1.bankc.repository.Interface.TransactionRepository;
import io.github.alirostom1.bankc.service.Interface.AccountService;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public AccountServiceImpl(AccountRepository accountRepo,TransactionRepository txRepo){
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    @Override
    public void createAccount(AccountRequestDto dto) {
        try{
            Account acc = AccountMapper.dtoToAccount(dto);
            accountRepo.save(acc);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(AccountRequestDto dto) {
        try{
            Account acc = AccountMapper.dtoToAccount(dto);
            accountRepo.update(acc);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<AccountResponseDto> findAccountById(String accountId) {
        try{
            return accountRepo.findById(accountId).stream().map(AccountMapper::accountToDto).findFirst();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<AccountResponseDto> findAccountByNumber(String number) {
        try{
            return accountRepo.findByNumber(number).stream().map(AccountMapper::accountToDto).findFirst();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public void deleteAccount(String accountId) {
        try{
            txRepo.deleteByAccountId(accountId);
            accountRepo.delete(accountId);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public List<AccountResponseDto> findAccountsByClientId(String clientId){
        try{
            return accountRepo.findByClientId(clientId).stream().map(AccountMapper::accountToDto).toList();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<AccountResponseDto> getAccountWithMaxBalance() {
        try{
            return accountRepo.findAll().stream().sorted(Comparator.comparingDouble(Account::getBalance).reversed()).map(AccountMapper::accountToDto).findFirst();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<AccountResponseDto> getAccountWithMinBalance() {
        try{
            return accountRepo.findAll().stream().sorted(Comparator.comparingDouble(Account::getBalance)).map(AccountMapper::accountToDto).findFirst();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }
    
}
