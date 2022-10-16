package org.fnt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApplicationConfiguration {
    private String databaseHost;
    private int databasePort;
    public String databaseName;
    private String databaseSchema;
    private String databaseURL;
    private String databaseLogin;
    private  String databasePassword;
    private int serverPort;


    public ApplicationConfiguration propertyInitialization() {
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
        return this;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getDatabaseLogin() {
        return databaseLogin;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public int getServerPort() {
        return serverPort;
    }
}
