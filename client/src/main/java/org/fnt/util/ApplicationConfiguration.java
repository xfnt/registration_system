package org.fnt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApplicationConfiguration {
    public static String serverHost;
    public static int serverPort;

    public static void propertyInitialization() {
        File propFile = new File("server.properties");
        try (InputStream fileInputStream = new FileInputStream(propFile)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(fileInputStream);

            serverHost = properties.getProperty("server_host", "localhost");
            serverPort = Integer.parseInt(properties.getProperty("server_port", "8081"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
