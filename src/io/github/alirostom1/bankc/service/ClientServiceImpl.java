package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Client;
import io.github.alirostom1.bankc.repository.Interface.ClientRepository;
import io.github.alirostom1.bankc.service.Interface.ClientService;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepo;


    public ClientServiceImpl(ClientRepository clientRepo){
        this.clientRepo = clientRepo;
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
            throw new RuntimeException("Internal error,please try again late!");
        }
    }

    @Override
    public double getTotalBalance(String clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalBalance'");
    }

    @Override
    public int getAccountCount(String clientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccountCount'");
    }
    
}
