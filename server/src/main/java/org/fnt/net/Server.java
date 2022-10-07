package org.fnt.net;

import org.fnt.handler.ClientHandler;
import org.fnt.model.entity.Sendable;
import org.fnt.model.message.Message;
import org.fnt.model.message.MessageType;
import org.fnt.util.ApplicationConfiguration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class Server {
    private Logger log = Logger.getLogger(this.getClass().getName());
    private Socket client;
    private Set<ClientHandler> clientHandlers;

    public Server() {
        clientHandlers = new HashSet<>();
    }
    public void start() {
        log.info("Server started...");

        try (ServerSocket serverSocket = new ServerSocket(ApplicationConfiguration.serverPort)) {
            while(true) {
                log.info("Server waiting a client...");
                client = serverSocket.accept();
                log.info("Client accepted : " + client);
                new ClientHandler(client, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
}
