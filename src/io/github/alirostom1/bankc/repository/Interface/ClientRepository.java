package io.github.alirostom1.bankc.repository.Interface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import io.github.alirostom1.bankc.model.entity.Client;

public interface ClientRepository extends BaseRepository<Client> {
    Optional<Client> findByEmail(String email) throws SQLException;
    List<Client> findByName(String name) throws SQLException;
}
