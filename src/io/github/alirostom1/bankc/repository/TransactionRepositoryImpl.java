package io.github.alirostom1.bankc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import io.github.alirostom1.bankc.model.entity.Transaction;
import io.github.alirostom1.bankc.model.enums.TransactionType;
import io.github.alirostom1.bankc.repository.Interface.TransactionRepository;

public class TransactionRepositoryImpl implements TransactionRepository {
    private final Connection connection;

    public TransactionRepositoryImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(Transaction t) throws SQLException {
        String query = "INSERT INTO transactions(id,date,amount,type,location,accountId) values(?,?,?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, t.id());
            stmt.setTimestamp(2, Timestamp.valueOf(t.date()));
            stmt.setDouble(3,t.amount());
            stmt.setString(4, t.type().toString());
            stmt.setString(5, t.location());
            stmt.setString(6,t.accountId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Transaction> findById(String id) throws SQLException {
        String query = "SELECT * from transactions where id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Transaction(
                                    rs.getString("id"),
                                    rs.getTimestamp("date").toLocalDateTime(),
                                    rs.getDouble("amount"),
                                    TransactionType.valueOf(rs.getString("type")),
                                    rs.getString("location"),
                                    rs.getString("clientId")
                ));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Transaction> findAll() throws SQLException {
        String query = "SELECT * from transactions";
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                                                rs.getString("id"),
                                                rs.getTimestamp("date").toLocalDateTime(), 
                                                rs.getDouble("amount"),
                                                TransactionType.valueOf(rs.getString("type")),
                                                rs.getString("location"),
                                                rs.getString("accountId")
                                            );
                transactions.add(transaction);
            }
            return transactions;
        }
    }

    @Override
    public void update(Transaction t) throws SQLException {
        String query = "UPDATE transactions set amount = ? ,date = ?,type = ?, location = ? where id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setDouble(1, t.amount());
            stmt.setTimestamp(2, Timestamp.valueOf(t.date()));
            stmt.setString(3,t.type().toString());
            stmt.setString(4,t.location());
            stmt.setString(5,t.id());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM transactions where id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Transaction> findByAccountId(String accountId) throws SQLException{
        String query = "SELECT * FROM transactions where accountId = ?";
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                                                rs.getString("id"),
                                                rs.getTimestamp("date").toLocalDateTime(), 
                                                rs.getDouble("amount"),
                                                TransactionType.valueOf(rs.getString("type")),
                                                rs.getString("location"),
                                                rs.getString("accountId")
                                            );
                transactions.add(transaction);
            }
            return transactions;
        }
    }

    @Override
    public List<Transaction> findByClientID(String clientId) throws SQLException{
        String query = "SELECT t.* FROM transactions t,accounts a where t.accountId = a.id and a.clientId = ?";
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,clientId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                                                rs.getString("id"),
                                                rs.getTimestamp("date").toLocalDateTime(), 
                                                rs.getDouble("amount"),
                                                TransactionType.valueOf(rs.getString("type")),
                                                rs.getString("location"),
                                                rs.getString("accountId")
                                            );
                transactions.add(transaction);
            }
            return transactions;
        }
    }

    @Override
    public List<Transaction> findByType(TransactionType type) throws SQLException{
        String query = "SELECT * FROM transactions where type = ?";
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,type.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(rs.getString("id"),rs.getTimestamp("date").toLocalDateTime(),rs.getDouble("amount"),TransactionType.valueOf(rs.getString("type")),rs.getString("location"),rs.getString("accountId"));
                transactions.add(transaction);
            }
            return transactions;
        }
    }

    @Override
    public List<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException{
        String query = "SELECT * FROM transactions where date > ? and date < ?";
        List<Transaction> transactions = new ArrayList<>(); 
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setTimestamp(1,Timestamp.valueOf(startDate));
            stmt.setTimestamp(2,Timestamp.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                                                rs.getString("id"),
                                                rs.getTimestamp("date").toLocalDateTime(), 
                                                rs.getDouble("amount"),
                                                TransactionType.valueOf(rs.getString("type")),
                                                rs.getString("location"),
                                                rs.getString("accountId")
                                            );
                transactions.add(transaction);
            }
            return transactions;
        }
    }

    @Override
    public List<Transaction> findByAmountRange(double minAmount, double maxAmount) throws SQLException {
        String query = "SELECT * from transactions where amount > ? and amount < ?";
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setDouble(1, minAmount);
            stmt.setDouble(2, maxAmount);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                                                rs.getString("id"),
                                                rs.getTimestamp("date").toLocalDateTime(), 
                                                rs.getDouble("amount"),
                                                TransactionType.valueOf(rs.getString("type")),
                                                rs.getString("location"),
                                                rs.getString("accountId")
                                            );
                transactions.add(transaction);
            }
            return transactions;
        }
    }

    @Override
    public List<Transaction> findByLocation(String location) throws SQLException {
        String query = "SELECT * from transaction where location LIKE ?";
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, "%" + location + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Transaction transaction = new Transaction(
                                                rs.getString("id"),
                                                rs.getTimestamp("date").toLocalDateTime(), 
                                                rs.getDouble("amount"),
                                                TransactionType.valueOf(rs.getString("type")),
                                                rs.getString("location"),
                                                rs.getString("accountId")
                                            );
                transactions.add(transaction);
            }
            return transactions;
        }
    }
}
