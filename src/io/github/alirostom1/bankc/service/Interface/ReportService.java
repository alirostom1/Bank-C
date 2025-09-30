package io.github.alirostom1.bankc.service.Interface;

import java.util.List;
import java.util.Map;

import io.github.alirostom1.bankc.model.entity.Client;
import io.github.alirostom1.bankc.model.entity.Transaction;

public interface ReportService {
    List<Client> getTop5ClientsByBalance();
    Map<String, Object> getMonthlyReport(int year, int month);
    Map<String, List<Transaction>> getSuspiciousTransactions(double thresholdAmount, String unusualLocation, int maxTransactionsPerMinute);
    List<String> getInactiveAccountIds(int monthsInactive);    
}