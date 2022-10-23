package com.example;

import com.example.net.Client;
import com.example.ui.UserInterface;
import com.example.util.ApplicationConfiguration;

public class ClientApplication {

    public static void main(String[] args) {
        ApplicationConfiguration.propertyInitialization();

        Client client = new Client();
        UserInterface ui = new UserInterface(client);
        ui.start();
    }
}
