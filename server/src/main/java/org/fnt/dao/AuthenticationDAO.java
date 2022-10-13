package org.fnt.dao;

import org.fnt.connection.ConnectionFactory;
import org.fnt.model.entity.AuthenticationInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            throw new RuntimeException(e);
        }
        close();
        return authenticationInformation;
    }

    @Override
    public List<AuthenticationInformation> getAll() {
        return null;
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
