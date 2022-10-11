package org.fnt.service;


import org.fnt.model.entity.Sendable;
import org.fnt.model.entity.Timetable;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;

import java.util.List;

public class TimetableService {
    private MessageService messageService;

    public TimetableService(MessageService messageService) {
        this.messageService = messageService;
    }

    public Message<Sendable> addTime(String currentUser, List<Timetable> timetableList) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        currentUser,
                        "-ADD_TIME",
                        timetableList));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getFreeTime(String currentUser) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        currentUser,
                        "-GET_FREE_TIME",
                        null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getAllTime(String currentUser) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        currentUser,
                        "-GET_ALL_TIME",
                        null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> appoint(String currentUser, Timetable timetable) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        currentUser,
                        "-APPOINTMENT",
                        List.of(timetable)));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getUserTimetable(String currentUser) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        currentUser,
                        "-GET_USER_TIMETABLE",
                        null));
        message = messageService.read();
        return message;
    }
}
