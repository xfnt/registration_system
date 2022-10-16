package org.fnt.service;

import org.fnt.connection.ConnectionFactory;
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
    public MessageService(ClientHandler clientHandler, ConnectionFactory connectionFactory) {
        this.clientHandler = clientHandler;
        authenticationService = new AuthenticationService(connectionFactory);
        userService = new UserService(connectionFactory);
        timetableService = new TimetableService(connectionFactory);
    }

    public void readRequest(Message message) {
        if(message == null) {
            return;
        }

        // Аутентификация
        if(message.getText().equals("-AUTHENTICATION")) {
            log.info(String.format("%s starts authentication", clientHandler.getConnectionId()));
            if(authenticationService.auth((AuthenticationInformation) message.getBody().get(0))) {
                clientHandler.auth(((AuthenticationInformation) message.getBody().get(0)).getLogin());
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-AUTHENTICATION_SUCCESS", null));
                log.info(String.format("%s ends authentication", clientHandler.getConnectionId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-AUTHENTICATION_FAILED", null));
                log.info("Something went wrong while authenticating a user...");
            }
        }

        // Первая часть регистрации (создаем информацию в таблице auth)
        if(message.getText().equals("-REGISTRATION")) {
            log.info(String.format("%s starts registration", clientHandler.getConnectionId()));
            if(authenticationService.registration((AuthenticationInformation) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-REGISTRATION_SUCCESS", null));
                log.info(String.format("%s ends registration", clientHandler.getConnectionId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-REGISTRATION_FAILED", null));
                log.info("Something went wrong while registering a user...");
            }
        }

        // Продолжение авторизации (Создание пользователя)
        if(message.getText().equals("-CREATE_USER")) {
            log.info(String.format("%s starts creating an account", clientHandler.getConnectionId()));
            if(userService.createUSer((User) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-CREATE_USER_SUCCESS", null));
                log.info(String.format("%s ends creating an account", clientHandler.getConnectionId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-CREATE_USER_FAILED", null));
                log.info("Something went wrong while creating a user...");
            }
        }

        // Получение конкретного пользователя по ID
        if(message.getText().equals("-GET_USER")) {
            log.info(String.format("%s starts getting user %s", clientHandler.getConnectionId(), message.getUserId()));
            User user = userService.getUserById(message.getUserId());
            if(user != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-RECIEVE_USER_SUCCESS", List.of(user)));
                log.info(String.format("%s ends getting user %s", clientHandler.getConnectionId(), message.getUserId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-EXCEPTION_WHILE_GET_USER", null));
                log.info("Something went wrong while getting user by ID...");
            }
        }

        // Редактирование пользователя
        if(message.getText().equals("-EDIT_USER")) {
            log.info(String.format("%s starts editing user %s", clientHandler.getConnectionId(), message.getUserId()));
            if(userService.editUserWithoutRigth((User) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-EDIT_USER_SUCCESS", null));
                log.info(String.format("%s ends editing user %s", clientHandler.getConnectionId(), message.getUserId()));

            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-CREATE_USER_FAILED", null));
                log.info("Something went wrong while editing user");
            }
        }

        // Получение списка всех пользователей
        if(message.getText().equals("-GET_ALL_USER")) {
            log.info(String.format("%s starts getting user list", clientHandler.getConnectionId()));
            List<User> userList = userService.getAll();
            if(userList != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-GET_ALL_USERS_SUCCESS", userList));
                log.info(String.format("%s ends getting user list", clientHandler.getConnectionId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-GET_ALL_USERS_FAILED", null));
                log.info("Something went wrong while getting user list");
            }
        }

        if(message.getText().equals("-UPDATE_USER_LIST")){
            log.info(String.format("%s starts updating user list", clientHandler.getConnectionId()));
            if(userService.updateAll(message.getBody().stream().map(u->(User) u).toList())) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-UPDATE_USER_LIST_SUCCESS", null));
                log.info(String.format("%s ends updating user list", clientHandler.getConnectionId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-UPDATE_USER_LIST_FAILED", null));
                log.info("Something went wrong while updating user list");
            }
        }

        // Добавление рабочего времени
        if(message.getText().equals("-ADD_TIME")) {
            log.info(String.format("%s starts adding time for %s", clientHandler.getConnectionId(), message.getUserId()));
            List<Timetable> existsTimetable = timetableService.addTime(message.getBody());
            if(existsTimetable != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-ADD_TIME_SUCCESS", existsTimetable));
                log.info(String.format("%s ends adding time for %s", clientHandler.getConnectionId(), message.getUserId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-ADD_TIME_FAILED", null));
                log.info(String.format("Something went wrong while adding time for %s", message.getUserId()));
            }
        }

        // Получение рассписания для сотрудника
        if(message.getText().equals("-GET_ALL_TIME")) {
            log.info(String.format("%s starts getting timetable for employee %s", clientHandler.getConnectionId(), message.getUserId()));
            List<Timetable> timeList = timetableService.getAllTime(message.getUserId(), UserType.EMPLOYEE);
            if(timeList != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-GET_ALL_TIME_SUCCESS", timeList));
                log.info(String.format("%s ends getting timetable for employee %s", clientHandler.getConnectionId(), message.getUserId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-ADD_ALL_TIME_FAILED", null));
                log.info(String.format("Something went wrong while getting timetable for employee %s", message.getUserId()));
            }
        }

        // Получение рассписания для пользователя
        if(message.getText().equals("-GET_USER_TIMETABLE")) {
            log.info(String.format("%s starts getting timetable for users %s", clientHandler.getConnectionId(), message.getUserId()));
            List<Timetable> timeList = timetableService.getAllTime(message.getUserId(), UserType.USER);
            if(timeList != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-GET_ALL_TIME_SUCCESS", timeList));
                log.info(String.format("%s ends getting timetable for users %s", clientHandler.getConnectionId(), message.getUserId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-ADD_ALL_TIME_FAILED", null));
                log.info(String.format("Something went wrong while getting timetable for users %s", message.getUserId()));
            }
        }

        // Получение списка всех сотрудников
        if(message.getText().equals("-GET_EMPPLOYEES")) {
            log.info(String.format("%s starts getting employees list for users %s", clientHandler.getConnectionId(), message.getUserId()));
            List<User> userList = userService.getAllByType(UserType.EMPLOYEE);
            if(userList != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-GET_EMPLOYEES_SUCCESS", userList));
                log.info(String.format("%s ends getting employees list for users %s", clientHandler.getConnectionId(), message.getUserId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-GET_EMPLOYEES_FAILED", null));
                log.info(String.format("Something went wrong while getting employees list for users %s", message.getUserId()));
            }
        }

        // Получение свободного времени для записи
        if(message.getText().equals("-GET_FREE_TIME")) {
            log.info(String.format("%s starts getting writable time for user %s", clientHandler.getConnectionId(), message.getUserId()));
            List<Timetable> freeTimeList = timetableService.getFreeTime(message.getUserId());
            if(freeTimeList != null) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-GET_FREE_TIME_SUCCESS", freeTimeList));
                log.info(String.format("%s ends getting writable time for user %s", clientHandler.getConnectionId(), message.getUserId()));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, "ERROR","-ADD_TIME_FAILED", null));
                log.info(String.format("Something went wrong while getting writable time for user %s", message.getUserId()));
            }
        }

        // Запись пользователя к сотруднику
        if(message.getText().equals("-APPOINTMENT")) {
            log.info(String.format("%s starts appointment for employee", clientHandler.getConnectionId()));
            if(timetableService.appoint((Timetable) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, String.valueOf(clientHandler.getConnectionId()),"-APPOINT_SUCCESS", null));
                log.info(String.format("%s ends appointment for employee", clientHandler.getConnectionId()));
            }else {
                log.info("Something went wrong while appointment for employee");
            }
        }

        // Корректный разрыв соединения с пользователем
        if(message.getText().equals("-EXIT")) {
            log.info("disconnecting user");
            clientHandler.changeStateToFalse();
        }
    }
}
