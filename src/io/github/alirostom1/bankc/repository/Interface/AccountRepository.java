package io.github.alirostom1.bankc.repository.Interface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Account;

public interface AccountRepository extends BaseRepository<Account> {
    List<Account> findByClientId(String clientId) throws SQLException;
    Optional<Account> findByNumber(String number) throws SQLException;
}
