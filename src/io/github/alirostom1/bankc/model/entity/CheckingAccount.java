package io.github.alirostom1.bankc.model.entity;

public final class CheckingAccount extends Account{
    private double overdraftLimit;
    public CheckingAccount(String id, String number, double balance, String clientId, double overdraftLimit) {
        super(id, number, balance, clientId);
        this.overdraftLimit = overdraftLimit;
    }
    public CheckingAccount( String number, double balance, String clientId, double overdraftLimit) {
        super(number, balance, clientId);
        this.overdraftLimit = overdraftLimit;   
    }
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}
