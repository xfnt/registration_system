package org.fnt.service;

import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;

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
