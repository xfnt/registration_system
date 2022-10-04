package org.fnt.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    DatabaseProperties.url,
                    DatabaseProperties.login,
                    DatabaseProperties.password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
