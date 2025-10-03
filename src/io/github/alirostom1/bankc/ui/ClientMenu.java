package io.github.alirostom1.bankc.ui;

import java.util.Optional;
import java.util.Scanner;

import io.github.alirostom1.bankc.model.dto.AccountRequestDto;
import io.github.alirostom1.bankc.model.dto.AccountResponseDto;
import io.github.alirostom1.bankc.model.dto.CheckingResponseDto;
import io.github.alirostom1.bankc.model.dto.ClientRequestDto;
import io.github.alirostom1.bankc.model.dto.ClientResponseDto;
import io.github.alirostom1.bankc.model.dto.SavingsResponseDto;
import io.github.alirostom1.bankc.service.Interface.AccountService;
import io.github.alirostom1.bankc.service.Interface.ClientService;

public class ClientMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService;
    private final AccountService accountService;

    public ClientMenu(ClientService clientService,AccountService accountService){
        this.clientService = clientService;
        this.accountService = accountService;
    }
    public void run(){
        while(true){
            try{
                System.out.println("********ClientMenu********");
                System.out.println("1. Create Client");
                System.out.println("2. Create Account");
                System.out.println("3. Update Client");
                System.out.println("4. Delete Client");
                System.out.println("5. View Client");
                System.out.println("6. Update Account");
                System.out.println("7. Delete Account");
                System.out.println("8. View Account");
                System.out.println("9. View my Accounts");
                System.out.println("10. Exit ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        createClient();
                        break;
                    case "2":
                        createAccount();
                        break;
                    case "3":
                        updateClient();
                        break;
                    case "4":
                        deleteClient();
                        break;
                    case "5":
                        viewClient();
                        break;
                    case "6":
                        updateAccount();
                        break;
                    case "7":
                        deleteAccount();
                        break;
                    case "8":
                        viewAccount();
                        break;
                    case "9":
                        viewMyAccounts();
                        break;
                    case "10":
                        return;
                    default:
                        break;
                }
            }catch(Exception e){
            }
        }
    }
  
    public void createClient(){
        try{
            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            if(clientService.findClientByName(name).isPresent()){
                System.out.println("User with name " + name + " already exists !");
                return;
            }
            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            if(clientService.findClientByEmail(email).isPresent()){
                System.out.println("User with email " + email + " already exists !");
                return;
            }
            ClientRequestDto request = new ClientRequestDto(null,name,email);
            clientService.addClient(request);
            System.out.println("Client added successfully !");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }        
    }
    public void createAccount(){
        try{
            System.out.println("Enter your name");
            String name = scanner.nextLine();
            Optional<ClientResponseDto> c = clientService.findClientByName(name);
            if(!c.isPresent()){
                System.out.println("Client with name "+ name +"not found !");
                return;
            }
            ClientResponseDto cDto = c.get();
            System.out.println("Enter type of your account(checking/savings): ");
            String type = scanner.nextLine().toLowerCase();
            if(!type.contains("check") && !type.contains("sav")){
                System.out.println("Invalid input, please type checking or savings !");
                return;
            }
            System.out.println("Enter account balance : ");
            double balance = Double.parseDouble(scanner.nextLine());
            if(balance < 0){
                System.out.println("Balance should be a positive number");
                return;
            }
            if(type.contains("check")){
                System.out.println("Enter overdraft limit: ");
                double overdraft = Double.parseDouble(scanner.nextLine());
                if(overdraft <= 0){
                    System.out.println("overdraft limit should be a positive number");
                    return;
                }
                AccountRequestDto acc = new AccountRequestDto(null,null,balance,cDto.id(),"checking",overdraft,0);
                accountService.createAccount(acc);
                System.out.println("Checking account created sucessfully !");
            }else{
                System.out.println("Enter interest rate(1-100)%: ");
                double interestRate = Double.parseDouble(scanner.nextLine());
                if(interestRate <= 0 || interestRate >=100){
                    System.out.println("Interest rate should be between 1 and 100 !");
                    return;
                }
                interestRate = interestRate / 100;
                AccountRequestDto acc = new AccountRequestDto(null,null,balance,cDto.id(),"savings",0,interestRate);
                accountService.createAccount(acc);
                System.out.println("Checking account created sucessfully !");
            }
        }catch(NumberFormatException e){
            System.out.println("input should be a number !");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void updateClient(){
        try{
            System.out.println("Enter client id to update:");
            String clientId = scanner.nextLine();
            Optional<ClientResponseDto> clientOp = clientService.findClientById(clientId);
            if(clientOp.isEmpty()){
                System.out.println("Client with id " + clientId + " not found !");  
                return;
            }
            ClientResponseDto existingClient = clientOp.get();
            System.out.println("Enter new name (current: " + existingClient.name() + "): ");
            String name = scanner.nextLine();
            if(name.isEmpty()){
                name = existingClient.name();
            }else if(clientService.findClientByName(name).isPresent()){
                System.out.println("User with name " + name + " already exists !");
                return;
            }
            System.out.println("Enter new email (current: " + existingClient.email() + "): ");
            String email = scanner.nextLine();
            if(email.isEmpty()){
                email = existingClient.email();
            }else if(clientService.findClientByEmail(email).isPresent()){
                System.out.println("User with email " + email + " already exists !");
                return;
            }
            ClientRequestDto updatedClient = new ClientRequestDto(clientId,name,email);
            clientService.updateClient(updatedClient);
            System.out.println("Client updated successfully !");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void deleteClient(){
        try{
            System.out.println("Enter client id to delete:");
            String clientId = scanner.nextLine();
            if(clientService.deleteClient(clientId)){
                System.out.println("Client deleted successfully !");
            }else{
                System.out.println("Client with id " + clientId + " not found !");  
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void viewClient(){
        try{
            System.out.println("Enter client name to view:");
            String clientId = scanner.nextLine();
            Optional<ClientResponseDto> clientOp = clientService.findClientByName(clientId);
            if(clientOp.isPresent()){
                System.out.println(clientOp.get());
            }else{
                System.out.println("Client with id " + clientId + " not found !");  
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void updateAccount(){
        try {
            System.out.println("Enter account number to update:");
            String number = scanner.nextLine();
            Optional<AccountResponseDto> accOp = accountService.findAccountByNumber(number);
            if (accOp.isEmpty()) {
                System.out.println("Account with number " + number + " not found!");
                return;
            }
            AccountResponseDto acc = accOp.get();
            System.out.println("Current balance: " + acc.balance());
            System.out.print("Enter new balance (leave blank to keep current): ");
            String balanceInput = scanner.nextLine();
            double balance = balanceInput.isBlank() ? acc.balance() : Double.parseDouble(balanceInput);

            String type = acc instanceof CheckingResponseDto ? "checking" : "savings";
            double overdraftLimit = 0;
            double interestRate = 0;
            if (type.equals("checking")) {
                overdraftLimit = ((CheckingResponseDto) acc).overdraftLimit();
                System.out.print("Current overdraft limit: " + overdraftLimit + ". Enter new overdraft limit (leave blank to keep current): ");
                String overdraftInput = scanner.nextLine();
                overdraftLimit = overdraftInput.isBlank() ? overdraftLimit : Double.parseDouble(overdraftInput);
            } else {
                interestRate = ((SavingsResponseDto) acc).interestRate();
                System.out.print("Current interest rate: " + (interestRate * 100) + "% Enter new interest rate (leave blank to keep current, as percent): ");
                String irInput = scanner.nextLine();
                interestRate = irInput.isBlank() ? interestRate : Double.parseDouble(irInput) / 100.0;
            }

            AccountRequestDto req = new AccountRequestDto(
                acc.id(),
                acc.number(),
                balance,
                acc.clientId(),
                type,
                overdraftLimit,
                interestRate
            );
            accountService.updateAccount(req);
            System.out.println("Account updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating account: " + e.getMessage());
        }
    }
    public void deleteAccount(){
        try{
            System.out.println("Enter account id to delete:");
            String accId = scanner.nextLine();
            accountService.deleteAccount(accId);
            System.out.println("Account deleted successfully !");
        }catch(Exception e){
            System.out.println("Error deleting account !");
        }
    }
    public void viewAccount(){
        try{
            System.out.println("Enter account number to view:");
            String number = scanner.nextLine();
            Optional<AccountResponseDto> accOp = accountService.findAccountByNumber(number);
            if(accOp.isPresent()){
                System.out.println(accOp.get());
            }else{
                System.out.println("Account with id " + number + " not found !");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void viewMyAccounts(){
        try{
            System.out.println("Enter client name:");
            String name = scanner.nextLine();
            Optional<ClientResponseDto> clientOp = clientService.findClientByName(name);
            if(clientOp.isEmpty()){
                System.out.println("Unregistered client name !");
                return;
            }
            ClientResponseDto client = clientOp.get();
            System.out.println("Accounts of client " + client.name() + ":"); 
            for(AccountResponseDto acc : accountService.findAccountsByClientId(client.id())){
                System.out.println(acc);
                System.out.println("****************");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
