package io.github.alirostom1.bankc.service.Interface;

import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.dto.ClientRequestDto;
import io.github.alirostom1.bankc.model.dto.ClientResponseDto;

public interface ClientService {
    void addClient(ClientRequestDto clientDto);
    void updateClient(ClientRequestDto clientDto);
    boolean deleteClient(String clientId);
    Optional<ClientResponseDto> findClientById(String clientId);
    Optional<ClientResponseDto> findClientByName(String name);
    List<ClientResponseDto> getAllClients();
    double getTotalBalance(String clientId);
    int getAccountCount(String clientId);
    Optional<ClientResponseDto> findClientByEmail(String email);
}
