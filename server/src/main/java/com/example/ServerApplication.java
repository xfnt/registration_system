package com.example;

import com.example.net.Server;
import com.example.util.ApplicationConfiguration;

public class ServerApplication {

    public static void main(String[] args) {
        ApplicationConfiguration configuration = new ApplicationConfiguration().propertyInitialization();
        Server server = new Server(configuration);
        server.start();
    }
}
