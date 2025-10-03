package io.github.alirostom1.bankc.service.Interface;

import java.util.List;
import java.util.Map;

import io.github.alirostom1.bankc.model.dto.ClientResponseDto;
import io.github.alirostom1.bankc.model.dto.TransactionResponseDto;

public interface ReportService {
    List<ClientResponseDto> getTop5ClientsByBalance();
    Map<String, Object> getMonthlyReport(int year, int month);
    Map<String, List<TransactionResponseDto>> getSuspiciousTransactions(double thresholdAmount, String unusualLocation, int maxTransactionsPerMinute);
    List<String> getInactiveAccountIds(int monthsInactive);    
}