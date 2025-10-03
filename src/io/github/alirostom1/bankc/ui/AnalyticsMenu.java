package io.github.alirostom1.bankc.ui;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import io.github.alirostom1.bankc.model.dto.ClientResponseDto;
import io.github.alirostom1.bankc.model.dto.TransactionRequestDto;
import io.github.alirostom1.bankc.model.dto.TransactionResponseDto;
import io.github.alirostom1.bankc.model.enums.TransactionType;
import io.github.alirostom1.bankc.service.Interface.ReportService;
import io.github.alirostom1.bankc.service.Interface.TransactionService;

public class AnalyticsMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ReportService reportService;
    private final TransactionService transactionService;

    public AnalyticsMenu(ReportService reportService,TransactionService transactionService){
        this.reportService = reportService;
        this.transactionService = transactionService;
    }
    public void run(){
        while(true){
            try{
                System.out.println("********AnalyticsMenu********");
                System.out.println("1. Top 5 clients");
                System.out.println("2. Transactions by Type");
                System.out.println("3. Transactions monthly repoort");
                System.out.println("4. Inactive accounts ");
                System.out.println("5. Suspicious transactions");
                System.out.println("7. Exit ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        top5clients();
                        break;
                    case "2":
                        txsByType();
                        break;
                    case "3":
                        txsMonthly();
                        break;
                    case "4":
                        inactiveAccs();
                        return;
                    case "5":
                        susTxs();
                        break;
                    case "6":
                        return;
                    default:
                        break;
                }
            }catch(Exception e){
            }
        }
    }
    public void top5clients(){
        try{
            System.out.println("****************");
            List<ClientResponseDto> clients = reportService.getTop5ClientsByBalance();
            for(ClientResponseDto c : clients){
                System.out.println(c);
                System.out.println("****************");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void txsByType(){
        try{
            System.out.println("Enter the transaction type (DEPOSIT,WITHDRAWAL,TRANSFER): ");
            String type = scanner.nextLine();
            if(!type.equals("DEPOSIT") && !type.equals("WITHDRAWAL") && !type.equals("TRANSFER")){
                System.out.println("Invalid transaction type !");
                return;
            }
            Map<TransactionType,List<TransactionResponseDto>>  txs = transactionService.groupTransactionsByType(type);
            List<TransactionResponseDto> list = txs.get(TransactionType.valueOf(type));
            if(list == null || list.size() == 0){
                System.out.println("No transactions found for type " + type);
                return;
            }
            System.out.println("****************");
            for(TransactionResponseDto tx : list){
                System.out.println(tx);
                System.out.println("****************");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
         
    }
    public void txsMonthly(){
        try{
            System.out.println("Enter the year and month(2025-03):");
            String date = scanner.nextLine();
            if(!date.matches("\\d{4}-\\d{2}")){
                System.out.println("Invalid date format !");
                return;
            }
            String[] dates = date.split("-");
            Map<String,Object> report = reportService.getMonthlyReport(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]));
            System.out.println("****************");
            for(Map.Entry<String,Object> entry : report.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
                System.out.println("****************");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void inactiveAccs(){
        try{
            System.out.println("Enter the number of months of inactivity:");
            int months = Integer.parseInt(scanner.nextLine());
            if(months <= 0){
                System.out.println("Number of months should be greater than 0 !");
                return;
            }
            List<String> ids = reportService.getInactiveAccountIds(months); 
            if(ids.size() == 0){
                System.out.println("no inactive accounts found !");
            }
            for(String id : ids){
                System.out.println("****************");
                System.out.println("Account id : " + id );
            }
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public void susTxs(){
        try{
            System.out.println("Enter threshold amount:");
            double thresholdAmount = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter unusual location (or press Enter to skip):");
            String unusualLocation = scanner.nextLine();
            System.out.println("Enter max transactions per minute:");
            int maxTransactionsPerMinute = Integer.parseInt(scanner.nextLine());
            Map<String, List<TransactionResponseDto>> report = reportService.getSuspiciousTransactions(thresholdAmount, unusualLocation, maxTransactionsPerMinute);
            List<TransactionResponseDto> byAmount = report
                .getOrDefault("By Amount", List.of());
            List<TransactionResponseDto> byLocation = report
                .getOrDefault("By Location", List.of());
            List<TransactionResponseDto> byTpm = report
                .getOrDefault("By TPM", List.of());
            System.out.println("Suspicious transactions by Amount:");
            for(TransactionResponseDto tx : byAmount){
                System.out.println("****************");
                System.out.println(tx);
            }
            System.out.println("Suspicious transactions by Location:");
            for(TransactionResponseDto tx : byLocation){
                System.out.println("****************");
                System.out.println(tx);
            }
            System.out.println("Suspicious transactions by TPM:");
            for(TransactionResponseDto tx : byTpm){
                System.out.println("****************");
                System.out.println(tx);
            } 
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
