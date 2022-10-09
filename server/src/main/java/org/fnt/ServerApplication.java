package org.fnt;

import org.fnt.net.Server;
import org.fnt.util.ApplicationConfiguration;

public class ServerApplication {

    public static void main(String[] args) {
        ApplicationConfiguration.propertyInitialization();
        Server server = new Server();
        server.start();
    }
}
