package io.github.alirostom1.bankc.repository.Interface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    void save(T entity) throws SQLException;
    Optional<T> findById(String id) throws SQLException;
    List<T> findAll() throws SQLException;
    void update(T entity) throws SQLException;
    void delete(String id) throws SQLException;
}