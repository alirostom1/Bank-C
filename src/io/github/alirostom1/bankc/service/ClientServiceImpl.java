package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.model.entity.Client;
import io.github.alirostom1.bankc.repository.Interface.AccountRepository;
import io.github.alirostom1.bankc.repository.Interface.ClientRepository;
import io.github.alirostom1.bankc.service.Interface.ClientService;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepo;
    private final AccountRepository accountRepo;


    public ClientServiceImpl(ClientRepository clientRepo,AccountRepository accountRepo){
        this.clientRepo = clientRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    public void addClient(Client client) {
        try{
            clientRepo.save(client);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public void updateClient(Client client) {
        try{
            clientRepo.update(client);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public boolean deleteClient(String clientId) {
        try{
            clientRepo.delete(clientId);
            return true;
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<Client> findClientById(String clientId) {
        try{
            return clientRepo.findById(clientId);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<Client> findClientByName(String name) {
        try{
            return clientRepo.findByName(name);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public List<Client> getAllClients() {
        try{
            return clientRepo.findAll();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public double getTotalBalance(String clientId) {
        try{
            return accountRepo.findByClientId(clientId).stream().mapToDouble(Account::getBalance).sum();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public int getAccountCount(String clientId) {
        try{
            return accountRepo.findByClientId(clientId).size();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }
    
}
