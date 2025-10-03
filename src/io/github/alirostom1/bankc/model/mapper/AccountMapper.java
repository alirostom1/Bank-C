package io.github.alirostom1.bankc.model.mapper;

import io.github.alirostom1.bankc.model.dto.AccountRequestDto;
import io.github.alirostom1.bankc.model.dto.AccountResponseDto;
import io.github.alirostom1.bankc.model.dto.CheckingResponseDto;
import io.github.alirostom1.bankc.model.dto.SavingsResponseDto;
import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.model.entity.CheckingAccount;
import io.github.alirostom1.bankc.model.entity.SavingsAccount;

public class AccountMapper {
    public static Account dtoToAccount(AccountRequestDto dto){
        if(dto.type().equals("checking")){
            return new CheckingAccount(dto.id(),dto.number(),dto.balance(), dto.clientId(), dto.overdraftLimit());
        }else{
            return new SavingsAccount(dto.id(),dto.number(),dto.balance(), dto.clientId(), dto.interestRate());
        }
    }
    public static AccountResponseDto accountToDto(Account acc){
        if(acc instanceof CheckingAccount){
            CheckingAccount checkAcc = (CheckingAccount) acc;
            return new CheckingResponseDto(checkAcc.getId(),checkAcc.getNumber(),checkAcc.getBalance(),checkAcc.getClientId(),checkAcc.getOverdraftLimit());
        }else{
            SavingsAccount saveAccount = (SavingsAccount) acc;
            return new SavingsResponseDto(saveAccount.getId(), saveAccount.getNumber(), saveAccount.getBalance(), saveAccount.getClientId(), saveAccount.getInterestRate());
        }
    }
}
