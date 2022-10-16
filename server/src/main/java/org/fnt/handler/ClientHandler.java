package org.fnt.handler;

import org.fnt.connection.ConnectionFactory;
import org.fnt.model.message.Message;
import org.fnt.net.Server;
import org.fnt.service.MessageService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler {

    private Logger log = Logger.getLogger(this.getClass().getName());
    private long connectionId;

    private Server server;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private MessageService requestService;

    private boolean isConnected = true;

    public ClientHandler(long connectionId, Socket socket, Server server, ConnectionFactory connectionFactory) {
        this.connectionId = connectionId;
        this.socket = socket;
        this.server = server;
        requestService = new MessageService(this, connectionFactory);
        start();
    }

    private void start() {
        new Thread(() -> {
            read();
            closeConnection();
        }).start();
    }

    public void read() {

        while(isConnected) {
            try {
                if(input == null) {
                    input = new ObjectInputStream(socket.getInputStream());
                }
                Message message = null;
                message = (Message) input.readObject();
                requestService.readRequest(message);
            }catch (IOException e) {
                isConnected = false;
                log.info("Something went wrong while reading message...");
            }catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void write(Message message) {

        try{
            if(output == null) {
                output = new ObjectOutputStream(socket.getOutputStream());
            }
            output.writeObject(message);
            output.reset();
        } catch(IOException e) {
            isConnected = false;
            log.info("Something went wrong while written message...");
        }
    }

    public void auth(String userId) {
        server.subscribe(this);
        log.info(connectionId + " registred on server...");
    }

    public void changeStateToFalse() {
        isConnected = false;
    }

    public long getConnectionId() {
        return connectionId;
    }

    public void closeConnection() {
        log.info("Client : " + socket + " disconnected...");
        server.unsubscribe(this);
        try {
            socket.close();
            if(input != null) {
                input.close();
            }
            if(output != null) {
                output.close();
            }
        } catch (IOException e) {
            log.info("Something went wrong while closing the connection...");
            isConnected = false;
        }
    }
}
