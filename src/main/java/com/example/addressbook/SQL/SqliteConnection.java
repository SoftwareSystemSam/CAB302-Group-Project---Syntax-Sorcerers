package com.example.addressbook.SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * This class is used to handle the connection to the SQLite database
 */
public class SqliteConnection {
    private static Connection userDbInstance = null;
    private static Connection screenTimeDbInstance = null;

    // Private constructor to prevent instantiation
    /**
     * Private constructor to prevent instantiation
     */
    private SqliteConnection() { }
    /**
     * This function is used to get the user database instance
     * @return The user database instance
     */
    public static Connection getUserDbInstance() {
        if (userDbInstance == null) {
            userDbInstance = createConnection("jdbc:sqlite:userDatabase.db");
        }
        return userDbInstance;
    }
    /**
     * This function is used to get the screen time database instance
     * @return The screen time database instance
     */
    public static Connection getScreenTimeDbInstance() {
        if (screenTimeDbInstance == null) {
            screenTimeDbInstance = createConnection("jdbc:sqlite:screenTimeTrackingDatabase.db");
        }
        return screenTimeDbInstance;
    }
    /**
     * This function is used to create a connection to the database
     * @param url The url of the database
     * @return The connection to the database
     */
    private static Connection createConnection(String url) {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println("Failed to connect to database: " + url);
            System.err.println(sqlEx.getMessage());
            return null;
        }
    }
}
