package org.fnt.handler;

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

    private Server server;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private MessageService requestService;

    private boolean isConnected = true;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        requestService = new MessageService(this);
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
            if(input == null) {
                try {
                    input = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = null;
            try {
                message = (Message) input.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            requestService.readRequest(message);
        }
    }

    public void write(Message message) {
        if(output == null) {
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            output.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void auth() {
        server.subscribe(this);
    }

    public void changeStateToFalse() {
        isConnected = false;
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
            throw new RuntimeException(e);
        }
    }
}
