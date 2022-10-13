package org.fnt.repository;

import org.fnt.connection.ConnectionFactory;
import org.fnt.dao.UserDAO;
import org.fnt.service.UserService;

public class UserRepository extends UserDAO {
    public UserRepository(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }
}
