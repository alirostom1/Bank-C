package io.github.alirostom1.bankc.model.dto;

public record ClientResponseDto(
    String id,
    String name,
    String email
){
    @Override
    public String toString(){
        return "Client details: \n"+
                "id: " + id + "\n" +
                "name: " + name + "\n"+
                "email: " + email; 
    }
}