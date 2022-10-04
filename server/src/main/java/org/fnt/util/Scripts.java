package org.fnt.util;
import org.fnt.connection.DatabaseProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Scripts {

    public static void propertyInitialization() {
        File propFile = new File("server.properties");
        try (InputStream fileInputStream = new FileInputStream(propFile)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            DatabaseProperties.host = properties.getProperty("host", "localhost");
            DatabaseProperties.port = Integer.parseInt(properties.getProperty("port", "5432"));
            DatabaseProperties.database = properties.getProperty("database_name", "postgres");
            DatabaseProperties.schema = properties.getProperty("schema_name", "public");
            DatabaseProperties.url = properties.getProperty("url", DatabaseProperties.url=String.format("jdbc:postgresql://%s:%d/%s?currentSchema=%s",
                    DatabaseProperties.host,
                    DatabaseProperties.port,
                    DatabaseProperties.database,
                    DatabaseProperties.schema));
            DatabaseProperties.login = properties.getProperty("login", "postgres");
            DatabaseProperties.password = properties.getProperty("password", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
