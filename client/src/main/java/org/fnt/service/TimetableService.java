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

    public Message<Sendable> addTime(String employeeId, List<Timetable> timetableList) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        employeeId,
                        "-ADD_TIME",
                        timetableList));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getFreeTime(String employeeId) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        employeeId,
                        "-GET_FREE_TIME",
                        null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getAllTime(String employeeId) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        employeeId,
                        "-GET_ALL_TIME",
                        null));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> appoint(String userId, Timetable timetable) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        userId,
                        "-APPOINTMENT",
                        List.of(timetable)));
        message = messageService.read();
        return message;
    }

    public Message<Sendable> getUserTimetable(String userId) {
        Message message = null;
        messageService.write(
                new Message(
                        MessageType.REQUEST,
                        userId,
                        "-GET_USER_TIMETABLE",
                        null));
        message = messageService.read();
        return message;
    }
}
