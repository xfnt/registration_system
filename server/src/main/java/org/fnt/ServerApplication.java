package org.fnt;

import org.fnt.net.Server;
import org.fnt.util.ApplicationConfiguration;

public class ServerApplication {

    public static void main(String[] args) {
        ApplicationConfiguration configuration = new ApplicationConfiguration().propertyInitialization();
        Server server = new Server(configuration);
        server.start();
    }
}
