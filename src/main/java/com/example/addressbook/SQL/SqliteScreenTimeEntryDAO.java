package com.example.addressbook.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteScreenTimeEntryDAO implements IScreenTimeEntryDAO {
    // Database connection and table

    private Connection connection;

    public SqliteScreenTimeEntryDAO(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS screen_time_entries ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "user_id INTEGER,"
                    + "application_name VARCHAR,"
                    + "duration BIGINT,"
                    + "start_time DATETIME,"
                    + "FOREIGN KEY (user_id) REFERENCE users(id) ON DELETE CASCADE" // This will link the user_id in this table to the users table id
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addScreenTimeEntry(ScreenTimeEntry entry) throws SQLException {
        // TODO add logic to implement screen time entry into database.
        Statement insertStatement = connection.createStatement();
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO screen_time (user_id, application_name, duration, start_time) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, entry.getUser().getId());
            pstmt.setString(2, entry.getApplicationName());
            pstmt.setLong(3, entry.getDuration());
            pstmt.setString(4, entry.getStartTime().toString()); //  Correct formatting? TODO check date time formatting
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}