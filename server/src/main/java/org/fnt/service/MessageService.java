package org.fnt.service;

import org.fnt.handler.ClientHandler;
import org.fnt.model.entity.AuthenticationInformation;
import org.fnt.model.entity.user.User;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;

import java.util.List;

public class MessageService {
    private ClientHandler clientHandler;
    private AuthenticationService authenticationService;
    private UserService userService;
    public MessageService(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        authenticationService = new AuthenticationService();
        userService = new UserService();
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
            if(userService.editUser((User) message.getBody().get(0))) {
                clientHandler.write(new Message(MessageType.RESPONSE, message.getUserId(),"-EDIT_USER_SUCCESS", null));
            }else {
                clientHandler.write(new Message(MessageType.ERROR, message.getUserId(),"-CREATE_USER_FAILED", null));
            }
        }

        // Корректный разрыв соединения с пользователем
        if(message.getText().equals("-EXIT")) {
            clientHandler.changeStateToFalse();
        }
    }
}
