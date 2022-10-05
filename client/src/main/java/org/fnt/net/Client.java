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

    public void start(JFrame ui) {
        log.info("Client started...");
        try {
            log.info("Client looking for a server...");
            socket = new Socket(ApplicationConfiguration.serverHost, ApplicationConfiguration.serverPort);
            log.info("Accepted : " + socket);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(ui, "Server is unavailable...");
            log.info("Server is unavailable...");
            System.exit(0);
        }
    }

    public Object read() {
        if(input == null) {
            try {
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return input.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
