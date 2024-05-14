package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
    protected Connection _connection;
    protected PreparedStatement preparedStatement;
    protected String request;
    protected ResultSet resultSet;

    protected BaseDAO(Connection connection) {
        this._connection = connection;
    }

    public abstract T save(T element) throws SQLException;


    protected void close () throws SQLException {
        if(!_connection.isClosed()){
            _connection.close();
        }
        if (!preparedStatement.isClosed()){
            preparedStatement.close();
        }
        if(!resultSet.isClosed()){
            resultSet.close();
        }
    }

}