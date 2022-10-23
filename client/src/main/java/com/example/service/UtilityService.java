package com.example.service;

import com.example.model.message.Message;
import com.example.model.message.MessageType;

public class UtilityService {

    private MessageService messageService;

    public UtilityService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void  exit(){
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        "",
                        "-EXIT",
                        null));

    }
}
