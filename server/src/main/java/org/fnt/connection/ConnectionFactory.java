package org.fnt.connection;

import org.fnt.util.ApplicationConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    ApplicationConfiguration.databaseURL,
                    ApplicationConfiguration.databaseLogin,
                    ApplicationConfiguration.databasePassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
