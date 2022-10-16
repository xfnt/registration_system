package org.fnt.net;

import org.fnt.connection.ConnectionFactory;
import org.fnt.handler.ClientHandler;
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
    private ApplicationConfiguration configuration;
    private ConnectionFactory connectionFactory;
    private long id = 1;

    public Server(ApplicationConfiguration configuration) {
        this.configuration = configuration;
        connectionFactory = new ConnectionFactory(configuration);
        clientHandlers = new HashSet<>();
    }
    public void start() {
        log.info("Server started...");

        try (ServerSocket serverSocket = new ServerSocket(configuration.getServerPort())) {
            while(true) {
                log.info("Server waiting a client...");
                client = serverSocket.accept();
                log.info("Client accepted : " + client);
                new ClientHandler(id, client, this, connectionFactory);
                id++;
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
