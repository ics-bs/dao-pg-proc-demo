package se.lu.ics.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionHandler {
    private final String connectionURL;
    private final Properties credentials = new Properties();

    public ConnectionHandler() throws IOException {
        Properties config = new Properties();
        String path = "/config/config.properties";

        try (InputStream input = getClass().getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("Config file not found: " + path);
            }

            config.load(input);
        }

        String databaseServerName =
                config.getProperty("database.server.name");
        String databaseServerPort =
                config.getProperty("database.server.port");
        String databaseName =
                config.getProperty("database.name");
        String databaseUserName =
                config.getProperty("database.user.name");
        String databaseUserPassword =
                config.getProperty("database.user.password");
        connectionURL = "jdbc:postgresql://"
                + databaseServerName + ":"
                + databaseServerPort + "/"
                + databaseName;
        credentials.setProperty("user", databaseUserName);
        credentials.setProperty("password", databaseUserPassword);
        credentials.setProperty("escapeSyntaxCallMode", "call");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, credentials);
    }
}
