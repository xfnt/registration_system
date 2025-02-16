package com.example.service;

import com.example.model.entity.user.UserType;
import com.example.connection.ConnectionFactory;
import com.example.model.entity.user.User;
import com.example.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository repository;

    public UserService(ConnectionFactory connectionFactory) {
        repository = new UserRepository(connectionFactory);
    }

    public boolean createUSer(User user) {
        return repository.insert(user);
    }

    public User getUserById(String id) {
        return repository.getById(id);
    }

    public boolean editUserWithoutRigth(User user){
        return repository.updateWithoutRight(user);
    }

    public boolean editUser(User user) {
        return repository.update(user);
    }

    public List<User> getAllByPage(int pageNumber, int pageSize) {
//        return repository.getAll();
        return repository.getAllByPage(pageNumber, pageSize);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public List<User> getAllByType(UserType type) {
        return repository.getAllByType(type);
    }

    public boolean updateAll(List<User> userList) {
        boolean success = true;
        for(User u : userList) {
            if(!editUser(u)) success = false;
        }
        return success;
    }
}