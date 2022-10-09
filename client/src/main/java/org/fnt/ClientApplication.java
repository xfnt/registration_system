package org.fnt;

import org.fnt.net.Client;
import org.fnt.ui.UserInterface;
import org.fnt.util.ApplicationConfiguration;

public class ClientApplication {

    public static void main(String[] args) {
        ApplicationConfiguration.propertyInitialization();

        Client client = new Client();
        UserInterface ui = new UserInterface(client);
        ui.start();
    }
}
