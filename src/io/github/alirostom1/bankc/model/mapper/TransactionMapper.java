package io.github.alirostom1.bankc.model.mapper;

import java.util.Arrays;
import java.util.List;

import io.github.alirostom1.bankc.model.dto.TransactionRequestDto;
import io.github.alirostom1.bankc.model.dto.TransactionResponseDto;
import io.github.alirostom1.bankc.model.dto.TransferRequestDto;
import io.github.alirostom1.bankc.model.entity.Transaction;
import io.github.alirostom1.bankc.model.enums.TransactionType;

public class TransactionMapper {
    public static Transaction dtoToTransaction(TransactionRequestDto dto,String accId){
        return new Transaction(null,null,dto.amount(),TransactionType.valueOf(dto.type().toUpperCase()),dto.location(),accId);
    }
    public static TransactionResponseDto transactionToDto(Transaction transaction){
        return new TransactionResponseDto(transaction.id(),transaction.date(),transaction.amount(),transaction.type(),transaction.location(),transaction.accountId());
    }
    public static List<Transaction> dtoToTransaction(TransferRequestDto dto,String accId1,String accId2){
        Transaction tx = new Transaction(null,null,dto.amount(),TransactionType.TRANSFER,dto.location(),accId1);
        Transaction tx2 = new Transaction(null,null,dto.amount(),TransactionType.TRANSFER,dto.location(),accId2);
        return Arrays.asList(tx,tx2);
    }
}
