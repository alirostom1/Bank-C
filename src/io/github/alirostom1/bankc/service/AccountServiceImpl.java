package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.repository.Interface.AccountRepository;
import io.github.alirostom1.bankc.service.Interface.AccountService;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepo;

    public AccountServiceImpl(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }

    @Override
    public void createAccount(Account acc) {
        try{
            accountRepo.save(acc);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public void updateAccount(Account acc) {
        try{
            accountRepo.update(acc);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<Account> findAccountById(String accountId) {
        try{
            return accountRepo.findById(accountId);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<Account> findAccountByNumber(String number) {
        try{
            return accountRepo.findByNumber(number);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public List<Account> findAccountsByClientId(String clientId) {
        try{
            return accountRepo.findByClientId(clientId);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<Account> getAccountWithMaxBalance() {
        try{
            return accountRepo.findAll().stream().sorted(Comparator.comparingDouble(Account::getBalance).reversed()).findFirst();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<Account> getAccountWithMinBalance() {
        try{
            return accountRepo.findAll().stream().sorted(Comparator.comparingDouble(Account::getBalance)).findFirst();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }
    
}
