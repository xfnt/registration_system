package org.fnt;

import org.fnt.net.Client;
import org.fnt.util.ApplicationConfiguration;

public class ClientApplication {

    public static void main(String[] args) {
        ApplicationConfiguration.propertyInitialization();

        Client client = new Client();
        client.start();
    }
}
