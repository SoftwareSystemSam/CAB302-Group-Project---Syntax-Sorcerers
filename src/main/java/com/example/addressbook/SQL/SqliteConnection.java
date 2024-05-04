package com.example.addressbook.SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static Connection userDbInstance = null;
    private static Connection screenTimeDbInstance = null;

    // Private constructor to prevent instantiation
    private SqliteConnection() { }

    public static Connection getUserDbInstance() {
        if (userDbInstance == null) {
            userDbInstance = createConnection("jdbc:sqlite:userDatabase.db");
        }
        return userDbInstance;
    }

    public static Connection getScreenTimeDbInstance() {
        if (screenTimeDbInstance == null) {
            screenTimeDbInstance = createConnection("jdbc:sqlite:screenTimeTrackingDatabase.db");
        }
        return screenTimeDbInstance;
    }

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
