package org.fnt.repository;


import org.fnt.connection.ConnectionFactory;
import org.fnt.dao.AuthenticationDAO;

public class AuthenticationRepository extends AuthenticationDAO {
    public AuthenticationRepository(ConnectionFactory connectionFactory){
        super(connectionFactory);
    }
}
