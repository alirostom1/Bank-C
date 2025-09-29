package io.github.alirostom1.bankc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Client;
import io.github.alirostom1.bankc.repository.Interface.ClientRepository;

public class ClientRepositoryImpl implements ClientRepository {
    private final Connection connection;

    public ClientRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Client c) throws SQLException {
        String query = "INSERT INTO clients (id, name, email) VALUES (?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, c.id());
            stmt.setString(2, c.name());
            stmt.setString(3, c.email());
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Client> findById(String id) throws SQLException {
        String query = "SELECT * FROM clients WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Client(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }else{
                return Optional.empty();
            }

        }
    }

    @Override
    public List<Client> findAll() throws SQLException{
        String query = "SELECT * FROM clients";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            List<Client> clients = new ArrayList<>();
            while(rs.next()){
                clients.add(new Client(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }
            return clients;

        }
    }

    @Override
    public void update(Client entity) throws SQLException{
        String query = "UPDATE clients SET name = ?, email = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, entity.name());
            stmt.setString(2, entity.email());
            stmt.setString(3, entity.id());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM clients WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Client> findByEmail(String email) throws SQLException{
        String query = "SELECT * FROM clients WHERE email = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Client(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }else{
                return Optional.empty();
            }

        }
    }

    @Override
    public Optional<Client> findByName(String name) throws SQLException{
        String query = "SELECT * FROM clients WHERE name = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Client(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("email")
                ));
            }
            return Optional.empty();

        }
    }
    
}
