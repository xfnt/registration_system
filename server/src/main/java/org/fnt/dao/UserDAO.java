package org.fnt.dao;

import org.fnt.connection.ConnectionFactory;
import org.fnt.model.entity.user.User;
import org.fnt.model.entity.user.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {
    private ConnectionFactory connectionFactory;
    private Connection connection;

    public UserDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User getById(String id) {
        connection = connectionFactory.getConnection();
        String sql = """
                SELECT * FROM users
                WHERE users.id=?
                """;
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setMiddleName(resultSet.getString("middle_name"));
                user.setBirthDate(resultSet.getTimestamp("birth_date").toLocalDateTime().toLocalDate());
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setType(UserType.valueOf(resultSet.getString("user_type")));
                user.setDeleted(resultSet.getBoolean("deleted"));
                user.setAdmin(resultSet.getBoolean("admin"));
            }

        } catch (SQLException e) {
            user = null;
            close();
            throw new RuntimeException(e);
        }

        close();
        return user;
    }

    @Override
    public List<User> getAll() {
        connection = connectionFactory.getConnection();
        String sql = """
                SELECT * FROM users;
                """;
        List<User> userList = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            userList = new ArrayList<>();
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setMiddleName(resultSet.getString("middle_name"));
                user.setBirthDate(resultSet.getTimestamp("birth_date").toLocalDateTime().toLocalDate());
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setType(UserType.valueOf(resultSet.getString("user_type")));
                user.setDeleted(resultSet.getBoolean("deleted"));
                user.setAdmin(resultSet.getBoolean("admin"));
                userList.add(user);
            }
        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }

        close();
        return userList;
    }

    public List<User> getAllByPage(int pageNumber, int pageSize) {
        connection = connectionFactory.getConnection();
        String sql = String.format("SELECT * FROM users ORDER BY id LIMIT %d OFFSET((%d)*%d);", pageSize, pageNumber, pageSize);
        List<User> userList = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            userList = new ArrayList<>();
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setMiddleName(resultSet.getString("middle_name"));
                user.setBirthDate(resultSet.getTimestamp("birth_date").toLocalDateTime().toLocalDate());
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setType(UserType.valueOf(resultSet.getString("user_type")));
                user.setDeleted(resultSet.getBoolean("deleted"));
                user.setAdmin(resultSet.getBoolean("admin"));
                userList.add(user);
            }
        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }

        close();
        return userList;
    }

    public List<User> getAllByType(UserType type) {
        connection = connectionFactory.getConnection();
        String sql = """
                SELECT * FROM users
                WHERE users.user_type=?;
                """;
        List<User> userList = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type.name());
            resultSet = preparedStatement.executeQuery();

            userList = new ArrayList<>();
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setMiddleName(resultSet.getString("middle_name"));
                user.setBirthDate(resultSet.getTimestamp("birth_date").toLocalDateTime().toLocalDate());
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setType(UserType.valueOf(resultSet.getString("user_type")));
                user.setDeleted(resultSet.getBoolean("deleted"));
                user.setAdmin(resultSet.getBoolean("admin"));
                userList.add(user);
            }
        } catch (SQLException e) {
            close();
            throw new RuntimeException(e);
        }

        close();
        return userList;
    }

    @Override
    public boolean insert(User object) {
        connection = connectionFactory.getConnection();
        String sql = """
                INSERT INTO users (id,first_name,last_name,middle_name,birth_date,phone_number,user_type,deleted,admin)
                    VALUES (?,?,?,?,?,?,?,?,?);
                """;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, object.getId());
            preparedStatement.setString(2, object.getFirstName());
            preparedStatement.setString(3, object.getLastName());
            preparedStatement.setString(4, object.getMiddleName());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(object.getBirthDate().atStartOfDay()));
            preparedStatement.setString(6, object.getPhoneNumber());
            preparedStatement.setString(7, object.getType().toString());
            preparedStatement.setBoolean(8, object.isDeleted());
            preparedStatement.setBoolean(9, object.isAdmin());
            preparedStatement.execute();
        } catch (SQLException e) {
            close();
            return  false;
        }
        close();
        return true;
    }

    public boolean updateWithoutRight(User object) {
        connection = connectionFactory.getConnection();
        String sql = """
                UPDATE users
                SET first_name=?,last_name=?,middle_name=?,birth_date=?,phone_number=?
                WHERE id=?;
                """;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, object.getFirstName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.setString(3, object.getMiddleName());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(object.getBirthDate().atStartOfDay()));
            preparedStatement.setString(5, object.getPhoneNumber());
            preparedStatement.setString(6, object.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            close();
            return  false;
        }
        close();
        return true;
    }

    @Override
    public boolean update(User object) {
        connection = connectionFactory.getConnection();
        String sql = """
                UPDATE users
                SET first_name=?,last_name=?,middle_name=?,birth_date=?,phone_number=?,user_type=?,deleted=?,admin=?
                WHERE id=?;
                """;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, object.getFirstName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.setString(3, object.getMiddleName());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(object.getBirthDate().atStartOfDay()));
            preparedStatement.setString(5, object.getPhoneNumber());
            preparedStatement.setString(6, object.getType().toString());
            preparedStatement.setBoolean(7, object.isDeleted());
            preparedStatement.setBoolean(8, object.isAdmin());
            preparedStatement.setString(9, object.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            close();
            return  false;
        }
        close();
        return true;
    }

    @Override
    public boolean delete(User object) {
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
