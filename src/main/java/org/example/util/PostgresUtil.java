package org.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Component for managing connection to a PostgreSQL database.
 */
@Component
public class PostgresUtil {
    @Value("${url.string}")
    private String URL;

    @Value("${userName.string}")
    private String USERNAME;

    @Value("${password.string}")
    private String PASSWORD;

    public PostgresUtil() {
        init();
    }

    /**
     * Establishes a connection to the PostgreSQL database using the specified URL, username, and password.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Loads the PostgreSQL JDBC driver.
     */
    private void init() {
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Exception loading driver...");
        }
    }
}