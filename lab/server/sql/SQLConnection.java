package ifmo.lab.server.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Sql connection.
 */
public class SQLConnection {
    private final String url;
    private final String username;
    private final String password;

    /**
     * Instantiates a new Sql connection.
     */
    public SQLConnection() {
        try {
            Properties properties = PropertiesProvider.getAppProperties();
            this.url = properties.getProperty("datasource.url");
            this.username = properties.getProperty("datasource.username");
            this.password = properties.getProperty("datasource.password");
        } catch (Exception e) {
            throw new RuntimeException("Error loading database properties", e);
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
