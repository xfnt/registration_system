package org.fnt.service;

import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;
import org.fnt.net.Client;

public class MessageService {

    private Client client;

    public MessageService(Client client) {
        this.client = client;
    }

    public Message read() {
        Message message = (Message) client.read();
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
