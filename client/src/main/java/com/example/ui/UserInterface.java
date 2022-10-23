package com.example.ui;

import com.example.model.entity.Sendable;
import com.example.model.message.Message;
import com.example.model.message.MessageType;
import com.example.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInterface {

    private Client client;

    public UserInterface(Client client) {
        this.client = client;
    }
    public void start() {
        EventQueue.invokeLater(() -> {
            JFrame main = new JFrame();
            main.setSize(new Dimension(668, 400));
            main.setResizable(false);
            main.setTitle("Registration Application");

            // Корректное закрытие приложения, отправляем запрос на закрытие соединения серверу
            main.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    client.write(new Message<Sendable>(MessageType.REQUEST,"", "-EXIT", null));
                    System.exit(0);
                }
            });

            main.setVisible(true);
            main.add(new MenuHolder(client).getPanel());
            client.start(main);

        });
    }
}
