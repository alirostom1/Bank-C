package io.github.alirostom1.bankc.service.Interface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.dto.AccountRequestDto;
import io.github.alirostom1.bankc.model.dto.AccountResponseDto;

public interface AccountService {
    void createAccount(AccountRequestDto acc);
    void updateAccount(AccountRequestDto acc);
    void deleteAccount(String accountId);
    Optional<AccountResponseDto> findAccountById(String accountId);
    Optional<AccountResponseDto> findAccountByNumber(String number);
    List<AccountResponseDto> findAccountsByClientId(String clientId);
    Optional<AccountResponseDto> getAccountWithMaxBalance();
    Optional<AccountResponseDto> getAccountWithMinBalance();
}
