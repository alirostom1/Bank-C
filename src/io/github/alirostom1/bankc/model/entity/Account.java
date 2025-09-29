package io.github.alirostom1.bankc.model.entity;

import java.util.UUID;

public sealed class Account permits CheckingAccount, SavingsAccount {
    private String id;
    private String number;
    private double balance;
    private String clientId;

    public Account(String id, String number, double balance, String clientId) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.clientId = clientId;
    }
    public Account(String number,double balance, String clientId) {
        this.id = UUID.randomUUID().toString();
        this.balance = balance;
        this.number = number;
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number){
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId){
        this.clientId = clientId;
    }
    
}
