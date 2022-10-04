package org.fnt.service;

import org.fnt.handler.ClientHandler;
import org.fnt.model.message.Message;

public class MessageService {
    private ClientHandler clientHandler;
    public MessageService(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void readRequest(Message message) {

    }
}
