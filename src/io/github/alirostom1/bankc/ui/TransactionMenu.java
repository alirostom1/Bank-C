package io.github.alirostom1.bankc.ui;

import java.lang.StackWalker.Option;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import io.github.alirostom1.bankc.model.dto.ClientResponseDto;
import io.github.alirostom1.bankc.model.dto.TransactionRequestDto;
import io.github.alirostom1.bankc.model.dto.TransferRequestDto;
import io.github.alirostom1.bankc.service.Interface.ClientService;
import io.github.alirostom1.bankc.service.Interface.TransactionService;
import io.github.alirostom1.bankc.model.dto.TransactionResponseDto;

public class TransactionMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final TransactionService transactionService;
    private final ClientService clientService;


    public TransactionMenu(TransactionService transactionService,ClientService clientService){
        this.transactionService = transactionService;
        this.clientService = clientService;
    }
    public void run(){
        while(true){
            try{
                System.out.println("********TransactionMenu********");
                System.out.println("1. Make a deposit");
                System.out.println("2. Make a withdrawal");
                System.out.println("3. Make a transfer");
                System.out.println("4. get All my transactions");
                System.out.println("5. get All my account transactions");
                System.out.println("6. Exit ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        deposit();
                        break;
                    case "2":
                        withdraw();
                        break;
                    case "3":
                        transfer();
                        break;
                    case "4":
                        allTxs();
                        return;
                    case "5":
                        allAccTxs();
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
    public void deposit(){
        try{
            System.out.println("Enter account number:");
            String accNumber = scanner.nextLine();
            System.out.println("Enter amount to deposit:");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter location of deposit: ");
            String location = scanner.nextLine();
            TransactionRequestDto dto = new TransactionRequestDto(amount,"deposit",location,accNumber);
            transactionService.recordTransaction(dto);
            System.out.println("Transaction successful");
        }catch(NumberFormatException e){
            System.out.println("input should be a number !");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void withdraw(){
        try{
            System.out.println("Enter account number:");
            String accNumber = scanner.nextLine();
            System.out.println("Enter amount to withdraw:");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter location of withdrawal: ");
            String location = scanner.nextLine();
            TransactionRequestDto dto = new TransactionRequestDto(amount,"withdrawal",location,accNumber);
            transactionService.recordTransaction(dto);
            System.out.println("Transaction successful");
        }catch(NumberFormatException e){
            System.out.println("input should be a number !");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void transfer(){
        try{
            System.out.println("Enter sender account number:");
            String sender = scanner.nextLine();
            System.out.println("Enter recipient account number:");
            String recipient = scanner.nextLine();
            System.out.println("Enter amount to transfer:");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter location of transfer: ");
            String location = scanner.nextLine();
            TransferRequestDto dto = new TransferRequestDto(amount, location, recipient, sender);
            transactionService.recordTransfer(dto);
            System.out.println("Transaction successful");
        }catch(NumberFormatException e){
            System.out.println("input should be a number !");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void allTxs(){
        try{
            System.out.println("Enter Client name;");
            String name = scanner.nextLine();
            Optional<ClientResponseDto> clientOp = clientService.findClientByName(name);
            if(clientOp.isEmpty()){
                System.out.println("Unregistered client name !");
                return;
            }  
            ClientResponseDto client = clientOp.get();
            List<TransactionResponseDto> txs = transactionService.getTransactionsByClient(client.id());
            if(txs.size() > 0){
                System.out.println("List of transactions for Mr/Mrs " + client.name());
                for(TransactionResponseDto tx : txs){
                    System.out.println("***************");
                    System.out.println(tx);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void allAccTxs(){
        try{
            System.out.println("Enrer the account number: ");
            String number = scanner.nextLine();
            List<TransactionResponseDto> txs = transactionService.getTransactionsByAccount(number);
            System.out.println("All transaction for account " + number + " :");
            for(TransactionResponseDto tx : txs){
                System.out.println("***************");
                System.out.println(tx);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
