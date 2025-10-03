package io.github.alirostom1.bankc.model.dto;

public record CheckingResponseDto(
    String id,
    String number,
    double balance,
    String clientId,
    double overdraftLimit
) implements AccountResponseDto
{
    @Override
    public String toString(){
        return "Account details: \n" +
                "   id: " + id + "\n"+
                "   number: " + number + "\n"+
                "   balance: " + balance + "\n"+
                "   client id: " + clientId + "\n"+
                "   interestRate: " + overdraftLimit + "\n";
    }
}

