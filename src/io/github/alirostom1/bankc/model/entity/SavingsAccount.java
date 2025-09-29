package io.github.alirostom1.bankc.model.entity;

public final class SavingsAccount extends Account{
    private double interestRate;
    public SavingsAccount(String id, String number, double balance, String clientId, double interestRate) {
        super(id, number, balance, clientId);
        this.interestRate = interestRate;
    }
    public SavingsAccount( String number, double balance, String clientId, double interestRate) {
        super(number, balance, clientId);
        this.interestRate = interestRate;   
    }
    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
}
