package io.github.alirostom1.bankc.service.Interface;

import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Client;

public interface ClientService {
    void addClient(Client client);
    void updateClient(Client client);
    boolean deleteClient(String clientId);
    Optional<Client> findClientById(String clientId);
    Optional<Client> findClientByName(String name);
    List<Client> getAllClients();
    double getTotalBalance(String clientId);
    int getAccountCount(String clientId);
}
