package org.fnt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApplicationConfiguration {
    public static String databaseHost;
    public static int databasePort;
    public static String databaseName;
    public static String databaseSchema;
    public static String databaseURL;
    public static String databaseLogin;
    public static  String databasePassword;

    public static int serverPort;

    public static void propertyInitialization() {
        File propFile = new File("server.properties");
        try (InputStream fileInputStream = new FileInputStream(propFile)) {
            java.util.Properties properties = new java.util.Properties();
            properties.load(fileInputStream);

            databaseHost = properties.getProperty("database_host", "localhost");
            databasePort = Integer.parseInt(properties.getProperty("database_port", "5432"));
            databaseName = properties.getProperty("database_name", "postgres");
            databaseSchema = properties.getProperty("database_schema", "public");
            databaseURL = properties.getProperty("database_url", String.format("jdbc:postgresql://%s:%d/%s?currentSchema=%s",
                    databaseHost,
                    databasePort,
                    databaseName,
                    databaseSchema));
            databaseLogin = properties.getProperty("database_login", "postgres");
            databasePassword = properties.getProperty("database_password", "");

            serverPort = Integer.parseInt(properties.getProperty("server_port", "8081"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
