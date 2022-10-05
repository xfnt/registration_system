package org.fnt.service;

import org.fnt.model.entity.user.User;
import org.fnt.model.entity.user.UserType;
import org.fnt.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository repository;

    public UserService() {
        repository = new UserRepository();
    }

    public boolean createUSer(User user) {
        return repository.insert(user);
    }

    public User getUserById(String id) {
        return repository.getById(id);
    }

    public boolean editUser(User user) {
        return repository.update(user);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public List<User> getAllByType(UserType type) {
        return repository.getAllByType(type);
    }
}