package com.example.repository;


import com.example.dao.AuthenticationDAO;
import com.example.connection.ConnectionFactory;

public class AuthenticationRepository extends AuthenticationDAO {
    public AuthenticationRepository(ConnectionFactory connectionFactory){
        super(connectionFactory);
    }
}
