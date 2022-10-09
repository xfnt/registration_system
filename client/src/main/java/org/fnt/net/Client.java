package org.fnt.net;

import org.fnt.model.message.Message;
import org.fnt.util.ApplicationConfiguration;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {
    private Logger log = Logger.getLogger(this.getClass().getName());

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private JFrame ui;

    public void start(JFrame ui) {
        log.info("Client started...");
        try {
            log.info("Client looking for a server...");
            socket = new Socket(ApplicationConfiguration.serverHost, ApplicationConfiguration.serverPort);
            log.info("Accepted : " + socket);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ui, "Сервер недоступен, попробуйте еще раз...");
            log.info("Server is unavailable...");
            System.exit(0);
        }
    }

    public Message read() {
        Message message = null;
        try {
            if(input == null) {
                input = new ObjectInputStream(socket.getInputStream());
            }
            message = (Message) input.readObject();

        }catch (IOException e) {
            disconnect();
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public void write(Message message) {

        try{
            if(output == null){
                output = new ObjectOutputStream(socket.getOutputStream());
            }
            output.writeObject(message);
            output.flush();
            output.reset();
        } catch (IOException e) {
            disconnect();
        }
    }

    public void closeConnection() {
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void disconnect() {
        closeConnection();
        JOptionPane.showMessageDialog(ui, "Соединение потеряно, перезапустите программу или обратитесь к администратору...");
        log.info("Server is unavailable...");
        System.exit(0);
    }
}
