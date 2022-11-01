package com.example.connection;

import com.example.util.ApplicationConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private com.example.util.ApplicationConfiguration ApplicationConfiguration;

    public ConnectionFactory(ApplicationConfiguration ApplicationConfiguration) {
        this.ApplicationConfiguration = ApplicationConfiguration;
    }

    public Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    ApplicationConfiguration.getDatabaseURL(),
                    ApplicationConfiguration.getDatabaseLogin(),
                    ApplicationConfiguration.getDatabasePassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
