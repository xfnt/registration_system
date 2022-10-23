package com.example.repository;

import com.example.connection.ConnectionFactory;
import com.example.dao.UserDAO;

public class UserRepository extends UserDAO {
    public UserRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }
}
