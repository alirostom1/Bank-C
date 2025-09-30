package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.model.entity.Client;
import io.github.alirostom1.bankc.model.entity.Transaction;
import io.github.alirostom1.bankc.model.enums.TransactionType;
import io.github.alirostom1.bankc.repository.Interface.AccountRepository;
import io.github.alirostom1.bankc.repository.Interface.ClientRepository;
import io.github.alirostom1.bankc.repository.Interface.TransactionRepository;
import io.github.alirostom1.bankc.service.Interface.ReportService;

public class ReportServiceImpl implements ReportService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ReportServiceImpl(ClientRepository clientRepository,AccountRepository accountRepository,TransactionRepository transactionRepository){
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Client> getTop5ClientsByBalance() {
        try{
            List<Client> clients = clientRepository.findAll();
            return clients.stream()
                .sorted(Comparator.comparingDouble(this::getTotalBalanceForClient).reversed()).limit(5).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
        
    }
    private double getTotalBalanceForClient(Client c){
        try{
            List<Account> accounts = accountRepository.findByClientId(c.id());
            return accounts.stream().mapToDouble(Account::getBalance).sum();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
        
    }

    @Override
    public Map<String, Object> getMonthlyReport(int year, int month) {
        try{
            YearMonth ym = YearMonth.of(year,month);
            LocalDateTime from = ym.atDay(1).atStartOfDay();
            LocalDateTime to = ym.atEndOfMonth().atTime(23,59,59);
            List<Transaction> txs = transactionRepository.findByDateRange(from, to);
            Map<TransactionType,Long> countByType = txs.stream().collect(Collectors.groupingBy(Transaction::type,Collectors.counting()));
            double totalVolume = txs.stream().mapToDouble(Transaction::amount).sum();
            Map<String,Object> repoort = new HashMap<>();
            repoort.put("countByType",countByType);
            repoort.put("totalVolume",totalVolume);
            return repoort;
            
        }catch(SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Map<String ,List<Transaction>> getSuspiciousTransactions(double thresholdAmount, String unusualLocation,
            int maxTransactionsPerMinute){
        try{
            List<Transaction> txs = transactionRepository.findAll();
            List<Transaction> susAmount = new ArrayList<>();
            susAmount.addAll(txs.stream().filter(t-> t.amount() > thresholdAmount).toList());
            
            List<Transaction> susLocation = new ArrayList<>();
            if(unusualLocation != null && !unusualLocation.isEmpty()){
                susLocation.addAll(txs.stream().filter(t -> t.location().contains(unusualLocation)).toList());
            }
            Map<String ,List<Transaction>> byAccount = txs.stream().collect(Collectors.groupingBy(Transaction::accountId));
            List<Transaction> susTpm = new ArrayList<>();
            for(List<Transaction> list : byAccount.values()){
                list.sort(Comparator.comparing(Transaction::date));
                for(int i = 0;i<list.size();i++){
                    int count = 1;
                    for(int j = 1; j<list.size();j++){
                        if(Duration.between(list.get(i).date(), list.get(j).date()).toSeconds() >= 60){
                            count++;
                        }
                    }
                    if(count > maxTransactionsPerMinute){
                        susTpm.add(list.get(i));
                        for(int j=i;j<count+i;j++){
                            susTpm.add(list.get(j));
                        }
                    }
                }
            }
            Map<String, List<Transaction>> map = Map.of("susAmount",susAmount,"susLocation",susLocation,"susTpm",susTpm);
            return map;
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public List<String> getInactiveAccountIds(int monthsInactive) {
        try{
            List<Account> accounts = accountRepository.findAll();
            LocalDateTime cutoff = LocalDateTime.now().minusMonths(monthsInactive);
            List<String> inactive = new ArrayList<>();
            for(Account acc : accounts){
                List<Transaction> txs = transactionRepository.findByAccountId(acc.getId());
                boolean active = txs.stream().anyMatch(t -> t.date().isAfter(cutoff));
                if(!active){
                    inactive.add(acc.getId());
                }
            }
            return inactive;
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }
    
}
