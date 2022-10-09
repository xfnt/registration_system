package org.fnt.service;

import org.fnt.model.entity.Sendable;
import org.fnt.model.entity.user.User;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;

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

    public Message<Sendable> editUser(User user) {
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

    public Message<Sendable> getAll() {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        "",
                        "-GET_ALL_USER",
                        null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getEmployees() {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        "",
                        "-GET_EMPPLOYEES",
                        null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> updateAll(List<User> changedUserList) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        "",
                        "-UPDATE_USER_LIST",
                        changedUserList));
        message = messageService.read();
        return message;
    }
}