package org.fnt.service;


import org.fnt.connection.ConnectionFactory;
import org.fnt.model.entity.AuthenticationInformation;
import org.fnt.repository.AuthenticationRepository;

public class AuthenticationService {
    private AuthenticationRepository repository;

    public AuthenticationService(ConnectionFactory factory) {
        repository = new AuthenticationRepository(factory);
    }

    public boolean auth(AuthenticationInformation authenticationInformation) {
        AuthenticationInformation auth = repository.getById(authenticationInformation.getLogin());
        if(auth.getLogin() == null) {
            return false;
        }

        if(auth.getLogin().equals(authenticationInformation.getLogin()) &&
        auth.getPassword().equals(authenticationInformation.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean registration(AuthenticationInformation authenticationInformation) {
        if(repository.insert(authenticationInformation)) {
            return true;
        }
        return false;
    }
}
