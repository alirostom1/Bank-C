package io.github.alirostom1.bankc.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.dto.ClientRequestDto;
import io.github.alirostom1.bankc.model.dto.ClientResponseDto;
import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.model.entity.Client;
import io.github.alirostom1.bankc.model.mapper.ClientMapper;
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
    public void addClient(ClientRequestDto clientDto) {
        try{
            Client client = ClientMapper.dtoToClient(clientDto);
            clientRepo.save(client);
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again later !",e);
        }
    }

    @Override
    public void updateClient(ClientRequestDto clientDto) {
        try{
            Client client = ClientMapper.dtoToClient(clientDto);
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
    public Optional<ClientResponseDto> findClientById(String clientId) {
        try{
            Optional<Client> client = clientRepo.findById(clientId);
            if(client.isPresent()){
                return Optional.of(ClientMapper.clientToDto(client.get()));
            }
            return Optional.empty();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public Optional<ClientResponseDto> findClientByName(String name) {
        try{
            Optional<Client> client = clientRepo.findByName(name);
            if(client.isPresent()){
                return Optional.of(ClientMapper.clientToDto(client.get()));
            }
            return Optional.empty();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        try{
            List<Client> clients = clientRepo.findAll();
            return clients.stream().map(ClientMapper::clientToDto).toList();
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
    @Override
    public Optional<ClientResponseDto> findClientByEmail(String email){
        try{
            Optional<Client> client = clientRepo.findByEmail(email);
            if(client.isPresent()){
                return Optional.of(ClientMapper.clientToDto(client.get()));
            }
            return Optional.empty();
        }catch(SQLException e){
            throw new RuntimeException("Internal error,please try again late!",e);
        }
    }
}
