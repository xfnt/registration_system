package com.example.service;

import com.example.model.entity.AuthenticationInformation;
import com.example.model.message.Message;
import com.example.model.message.MessageType;

import java.util.List;
import java.util.logging.Logger;

public class AuthenticationService {
    private Logger log = Logger.getLogger(this.getClass().getName());

    private MessageService messageService;

    public AuthenticationService(MessageService messageService) {
        this.messageService = messageService;
    }


    public Message authenticate(AuthenticationInformation authenticationInformation) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        authenticationInformation.getLogin(),
                        "-AUTHENTICATION",
                        List.of(authenticationInformation)));

        message = messageService.read();
        return message;
    }

    public Message registration(AuthenticationInformation authenticationInformation) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        authenticationInformation.getLogin(),
                        "-REGISTRATION",
                        List.of(authenticationInformation)));

        message = messageService.read();
        return message;
    }
}
