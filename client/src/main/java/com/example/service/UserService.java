package com.example.service;

import com.example.model.entity.Sendable;
import com.example.model.entity.user.User;
import com.example.model.message.Message;
import com.example.model.message.MessageType;

import java.util.List;

public class UserService {
    private MessageService messageService;

    public UserService(MessageService messageService) {
        this.messageService = messageService;
    }


    public Message createUser(User user) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        user.getId(),
                        "-CREATE_USER",
                        List.of(user)));
        message = messageService.read();
        return message;
    }

    public Message getUserById(String userId) {
        Message message = null;
        messageService.write(new Message(
                MessageType.REQUEST,
                userId,
                "-GET_USER",
                null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> editUser(String currentUser, User user) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        user.getId(),
                        "-EDIT_USER",
                        List.of(user)));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getAll(String currentUser, int pageNumber, int pageSize) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        "",
                        "-GET_ALL_USER",
                        null,
                        pageNumber,
                        pageSize
                        )
        );
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getEmployees(String currentUser) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        currentUser,
                        "-GET_EMPPLOYEES",
                        null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> updateAll(String currentUser, List<User> changedUserList) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        currentUser,
                        "-UPDATE_USER_LIST",
                        changedUserList));
        message = messageService.read();
        return message;
    }
}