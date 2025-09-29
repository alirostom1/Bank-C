package io.github.alirostom1.bankc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Account;
import io.github.alirostom1.bankc.model.entity.CheckingAccount;
import io.github.alirostom1.bankc.model.entity.SavingsAccount;
import io.github.alirostom1.bankc.repository.Interface.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {
    private final Connection connection;
    public AccountRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Account acc) throws SQLException {
        String query = "INSERT INTO accounts (id, number, balance, client_id,accountType) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, acc.getId());
            stmt.setString(2, acc.getNumber());
            stmt.setDouble(3, acc.getBalance());
            stmt.setString(4, acc.getClientId());
            if(acc instanceof SavingsAccount){
                stmt.setString(5, "SAVINGS");
            }else{
                stmt.setString(5, "CHECKING");
            }
            stmt.executeUpdate();
            if(acc instanceof SavingsAccount){
                String savingsQuery = "INSERT INTO savings_accounts (id, interest_rate) VALUES (?, ?)";
                try(PreparedStatement savingsStmt = connection.prepareStatement(savingsQuery)){
                    savingsStmt.setString(1, acc.getId());
                    savingsStmt.setDouble(2, ((SavingsAccount) acc).getInterestRate());
                    savingsStmt.executeUpdate();
                }
            }else{
                String checkingQuery = "INSERT INTO checking_accounts (id,overdraft_limit) VALUES (?)";
                try(PreparedStatement checkingStmt = connection.prepareStatement(checkingQuery)){
                    checkingStmt.setString(1, acc.getId());
                    checkingStmt.setDouble(2, ((CheckingAccount) acc).getOverdraftLimit());
                    checkingStmt.executeUpdate();
                }
            }
        }
    }

    @Override
    public Optional<Account> findById(String id) throws SQLException {
        String query = "SELECT * FROM accounts WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String accountType = rs.getString("accountType");
                if(accountType.equals("SAVINGS")){
                    String savingsQuery = "SELECT * FROM savings_accounts WHERE account_id = ?";
                    try(PreparedStatement savingsStmt = connection.prepareStatement(savingsQuery)){
                        savingsStmt.setString(1, id);
                        ResultSet savingsRs = savingsStmt.executeQuery();
                        if(savingsRs.next()){
                            return Optional.of(new SavingsAccount(
                                rs.getString("id"),
                                rs.getString("number"),
                                rs.getDouble("balance"),
                                rs.getString("client_id"),
                                savingsRs.getDouble("interest_rate")
                            ));
                        }
                    }
                }else if(accountType.equals("CHECKING")){
                    String checkingQuery = "SELECT * FROM checking_accounts WHERE account_id = ?";
                    try(PreparedStatement checkingStmt = connection.prepareStatement(checkingQuery)){
                        checkingStmt.setString(1, id);
                        ResultSet checkingRs = checkingStmt.executeQuery();
                        if(checkingRs.next()){
                            return Optional.of(new CheckingAccount(
                                rs.getString("id"),
                                rs.getString("number"),
                                rs.getDouble("balance"),
                                rs.getString("client_id"),
                                checkingRs.getDouble("overdraft_limit")
                            ));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() throws SQLException {
        String query = "SELECT * FROM accounts";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while(rs.next()){
                String accountType = rs.getString("accountType");
                if(accountType.equals("SAVINGS")){
                    String savingsQuery = "SELECT * FROM savings_accounts WHERE id = ?";
                    try(PreparedStatement savingsStmt = connection.prepareStatement(savingsQuery)){
                        savingsStmt.setString(1, rs.getString("id"));
                        ResultSet savingsRs = savingsStmt.executeQuery();
                        if(savingsRs.next()){
                            accounts.add(new SavingsAccount(
                                rs.getString("id"),
                                rs.getString("number"),
                                rs.getDouble("balance"),
                                rs.getString("client_id"),
                                savingsRs.getDouble("interest_rate")
                            ));
                        }
                    }
                }else if(accountType.equals("CHECKING")){
                    String checkingQuery = "SELECT * FROM checking_accounts WHERE id = ?";
                    try(PreparedStatement checkingStmt = connection.prepareStatement(checkingQuery)){
                        checkingStmt.setString(1, rs.getString("id"));
                        ResultSet checkingRs = checkingStmt.executeQuery();
                        if(checkingRs.next()){
                            accounts.add(new CheckingAccount(
                                rs.getString("id"),
                                rs.getString("number"),
                                rs.getDouble("balance"),
                                rs.getString("client_id"),
                                checkingRs.getDouble("overdraft_limit")
                            ));
                        }
                    }
                }
            }
            return accounts;
        }
    }

    @Override
    public void update(Account acc) throws SQLException {
        String query = "UPDATE accounts SET number = ?, balance = ?, client_id = ? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, acc.getNumber());
            stmt.setDouble(2, acc.getBalance());
            stmt.setString(3, acc.getClientId());
            stmt.setString(4, acc.getId());
            stmt.executeUpdate();
            if(acc instanceof SavingsAccount){
                String savingsQuery = "UPDATE savings_accounts SET interest_rate = ? WHERE account_id = ?";
                try(PreparedStatement savingsStmt = connection.prepareStatement(savingsQuery)){
                    savingsStmt.setDouble(1, ((SavingsAccount) acc).getInterestRate());
                    savingsStmt.setString(2, acc.getId());
                    savingsStmt.executeUpdate();
                }
            }else if(acc instanceof CheckingAccount){
                String checkingQuery = "UPDATE checking_accounts SET overdraft_limit = ? WHERE account_id = ?";
                try(PreparedStatement checkingStmt = connection.prepareStatement(checkingQuery)){
                    checkingStmt.setDouble(1, ((CheckingAccount) acc).getOverdraftLimit());
                    checkingStmt.setString(2, acc.getId());
                    checkingStmt.executeUpdate();
                }
            }
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String query = "SELECT accountType FROM accounts WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String accountType = rs.getString("accountType");
                if(accountType.equals("SAVINGS")){
                    String savingsQuery = "DELETE FROM savings_accounts WHERE account_id = ?";
                    try(PreparedStatement savingsStmt = connection.prepareStatement(savingsQuery)){
                        savingsStmt.setString(1, id);
                        savingsStmt.executeUpdate();
                    }
                }else if(accountType.equals("CHECKING")){
                    String checkingQuery = "DELETE FROM checking_accounts WHERE account_id = ?";
                    try(PreparedStatement checkingStmt = connection.prepareStatement(checkingQuery)){
                        checkingStmt.setString(1, id);
                        checkingStmt.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
    public List<Account> findByClientId(String clientId)throws SQLException{
        String query = "select * from accounts where clientId = ?";
        List<Account> accounts = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String accountType = rs.getString("accountType");
                if(accountType.equals("SAVINGS")){
                    String savingsQuery = "SELECT * from savings_account where id = ?";
                    try(PreparedStatement savingsStmt = connection.prepareStatement(savingsQuery)){
                        savingsStmt.setString(1, rs.getString("id"));
                        ResultSet savingsRs = savingsStmt.executeQuery();
                        if(savingsRs.next()){
                            SavingsAccount savingsAccount = new SavingsAccount(rs.getString("id"),rs.getString("number"),rs.getDouble("balance"),rs.getString("clientId"),savingsRs.getDouble("interestRate"));
                            accounts.add(savingsAccount);
                        }
                    }
                }else if(accountType.equals("CHECKING")){
                    String checkingQuery = "SELECT * from checkings_account where id = ?";
                    try(PreparedStatement checkingStmt = connection.prepareStatement(checkingQuery)){
                        checkingStmt.setString(1, rs.getString("id"));
                        ResultSet checkingRs = checkingStmt.executeQuery();
                        if(checkingRs.next()){
                            CheckingAccount checkingAccount = new CheckingAccount(rs.getString("id"),rs.getString("number"),rs.getDouble("balance"),rs.getString("clientId"),checkingRs.getDouble("overdraft_limit"));
                            accounts.add(checkingAccount);
                        }
                    }
                }
            }
            return accounts;
        }
    }

    @Override
    public Optional<Account> findByNumber(String number) throws SQLException{
        String query = "SELECT * from accounts where number = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String accountType = rs.getString("accountType");
                if(accountType.equals("SAVINGS")){
                    String savingsQuery = "SELECT * FROM savings_account where id = ?";
                    try(PreparedStatement savingsStmt = connection.prepareStatement(savingsQuery)){
                        savingsStmt.setString(1,rs.getString("id"));
                        ResultSet savingsRs = savingsStmt.executeQuery();
                        if(savingsRs.next()){
                            return Optional.of(new SavingsAccount(rs.getString("id"),rs.getString("number"),rs.getDouble("balance"),rs.getString("clientId"),savingsRs.getDouble("interestRate")));
                        }
                    }
                }else if(accountType.equals("CHECKING")){
                    String checkingQuery = "SELECT * FROM checkings_account where id = ?";
                    try(PreparedStatement checkingStmt = connection.prepareStatement(checkingQuery)){
                        checkingStmt.setString(1,rs.getString("id"));
                        ResultSet checkingRs = checkingStmt.executeQuery();
                        if(checkingRs.next()){
                            return Optional.of(new CheckingAccount(rs.getString("id"),rs.getString("number"),rs.getDouble("balance"),rs.getString("clientId"),checkingRs.getDouble("overdraft_limit")));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
    
}
