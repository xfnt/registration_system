package com.example.service;

import com.example.net.Client;
import com.example.model.message.Message;
import com.example.model.message.MessageType;

public class MessageService {

    private Client client;

    public MessageService(Client client) {
        this.client = client;
    }

    public Message read() {
        Message message = client.read();
        if(message.getType().equals(MessageType.RESPONSE) || message.getType().equals(MessageType.ERROR)) {
            return message;
        }
        return new Message(MessageType.ERROR,"",  "Упс, что-то пошло не так, обратитесь к разработчику", null);
    }

    public void write(Message message) {
        if(message.getType().equals(MessageType.REQUEST)) {
            client.write(message);
        }
        return;
    }
}
