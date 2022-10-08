package org.fnt.service;

import org.fnt.handler.ClientHandler;
import org.fnt.model.entity.AuthenticationInformation;
import org.fnt.model.entity.Timetable;
import org.fnt.model.entity.user.User;
import org.fnt.model.entity.user.UserType;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;

import java.util.List;
import java.util.logging.Logger;

public class MessageService {
    private Logger log = Logger.getLogger(this.getClass().getName());
    private ClientHandler clientHandler;
    private AuthenticationService authenticationService;
    private UserService userService;
    private TimetableService timetableService;
    public MessageService(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        authenticationService = new AuthenticationService();
        userService = new UserService();
        timetableService = new TimetableService();
    }

    public void readRequest(Message message) {
        if(message == null) {
            return;
        }

        // Аутентификация
        if(message.getText().equals("-AUTHENTICATION")) {
            if(authenticationService.auth((AuthenticationInformation) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-AUTHENTICATION_SUCCESS", null));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-AUTHENTICATION_FAILED", null));
            }
        }

        // Первая часть регистрации (создаем информацию в таблице auth)
        if(message.getText().equals("-REGISTRATION")) {
            if(authenticationService.registration((AuthenticationInformation) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-REGISTRATION_SUCCESS", null));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-REGISTRATION_FAILED", null));
            }
        }

        // Продолжение авторизации (Создание пользователя)
        if(message.getText().equals("-CREATE_USER")) {
            if(userService.createUSer((User) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-CREATE_USER_SUCCESS", null));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-CREATE_USER_FAILED", null));
            }
        }

        // Получение конкретного пользователя по ID
        if(message.getText().equals("-GET_USER")) {
            User user = userService.getUserById(message.getUserId());
            if(user != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-RECIEVE_USER_SUCCESS", List.of(user)));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-EXCEPTION_WHILE_GET_USER", null));
            }
        }

        // Редактирование пользователя
        if(message.getText().equals("-EDIT_USER")) {
            if(userService.editUserWithoutRigth((User) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-EDIT_USER_SUCCESS", null));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-CREATE_USER_FAILED", null));
            }
        }

        // Получение списка всех пользователей
        if(message.getText().equals("-GET_ALL_USER")) {
            List<User> userList = userService.getAll();
            if(userList != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-GET_ALL_USERS_SUCCESS", userList));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-GET_ALL_USERS_FAILED", null));
            }
        }

        if(message.getText().equals("-UPDATE_USER_LIST")){
            if(userService.updateAll(message.getBody().stream().map(u->(User) u).toList())) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-UPDATE_USER_LIST_SUCCESS", null));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-UPDATE_USER_LIST_FAILED", null));
            }
        }

        // Добавление рабочего времени
        if(message.getText().equals("-ADD_TIME")) {
            List<Timetable> existsTimetable = timetableService.addTime(message.getBody());
            if(existsTimetable != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-ADD_TIME_SUCCESS", existsTimetable));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-ADD_TIME_FAILED", null));
            }
        }

        // Получение рассписания для сотрудника
        if(message.getText().equals("-GET_ALL_TIME")) {
            List<Timetable> timeList = timetableService.getAllTime(message.getUserId(), UserType.EMPLOYEE);
            if(timeList != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-GET_ALL_TIME_SUCCESS", timeList));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-ADD_ALL_TIME_FAILED", null));
            }
        }

        // Корректный разрыв соединения с пользователем
        if(message.getText().equals("-EXIT")) {
            clientHandler.changeStateToFalse();
        }
    }
}
