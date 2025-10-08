package ma.fstt.dao;
import java.sql.*;
import java.util.List;

import jakarta.inject.Inject;

public abstract class DAO<T> {
    @Inject

    protected Connection connection;
    protected PreparedStatement Preparedstatement;
    protected ResultSet resultSet;
    protected Statement statement;

    public abstract void create(T entity) throws SQLException;
    public abstract void update(T entity) throws SQLException;
    public abstract void delete(T entity) throws SQLException;
    public abstract T findOne(T entity) throws SQLException;
    public abstract List<T> findAll () throws SQLException;

}
