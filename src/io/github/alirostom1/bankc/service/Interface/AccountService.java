package io.github.alirostom1.bankc.service.Interface;

import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Account;

public interface AccountService {
    void createAccount(Account acc);
    void updateAccount(Account acc);
    Optional<Account> findAccountById(String accountId);
    Optional<Account> findAccountByNumber(String number);
    List<Account> findAccountsByClientId(String clientId);
    Optional<Account> getAccountWithMaxBalance();
    Optional<Account> getAccountWithMinBalance();
}
