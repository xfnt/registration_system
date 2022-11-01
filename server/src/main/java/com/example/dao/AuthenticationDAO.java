package com.example.dao;

import com.example.connection.ConnectionFactory;
import com.example.model.entity.AuthenticationInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AuthenticationDAO implements DAO<AuthenticationInformation> {
    private ConnectionFactory connectionFactory;
    private Connection connection;

    public AuthenticationDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public AuthenticationInformation getById(String id) {
        connection = connectionFactory.getConnection();
        String sql = """
                SELECT * FROM auth
                WHERE auth.login=?
                """;
        AuthenticationInformation authenticationInformation = new AuthenticationInformation();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                authenticationInformation.setLogin(resultSet.getString("login"));
                authenticationInformation.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            close();
            return new AuthenticationInformation();
        }
        close();
        return authenticationInformation;
    }

    @Override
    public List<AuthenticationInformation> getAll() {
        return Collections.emptyList();
    }

    @Override
    public boolean insert(AuthenticationInformation object) {
        connection = connectionFactory.getConnection();
        String sql = """
                INSERT INTO auth (login,password)
                    VALUES (?,?);
                """;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, object.getLogin());
            preparedStatement.setString(2, object.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            close();
            return  false;
        }
        close();
        return true;
    }

    @Override
    public boolean update(AuthenticationInformation object) {
        return false;
    }

    @Override
    public boolean delete(AuthenticationInformation object) {
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
