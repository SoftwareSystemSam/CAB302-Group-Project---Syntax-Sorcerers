package com.example.addressbook.SQL;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqliteScreenTimeEntryDAO implements IScreenTimeEntryDAO {
    // Database connection and table

    private Connection connection;

    public SqliteScreenTimeEntryDAO(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
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
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" // Corrected the 'REFERENCE' to 'REFERENCES'
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addScreenTimeEntry(ScreenTimeEntry entry) throws SQLException {

        Statement insertStatement = connection.createStatement();
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO screen_time_entries (user_id, application_name, duration, start_time) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, entry.getUserId());
            pstmt.setString(2, entry.getApplicationName());
            pstmt.setLong(3, entry.getDuration());
            pstmt.setString(4, entry.getStartTime().toString()); //  Correct formatting? TODO check date time formatting
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ScreenTimeEntry> getScreenTimeEntriesByUserId(int userId) throws SQLException {
        List<ScreenTimeEntry> entries = new ArrayList<>();
        String query = "SELECT * FROM screen_time_entries WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                entries.add(new ScreenTimeEntry(
                        userId,
                        rs.getString("application_name"),
                        rs.getLong("duration"),
                        LocalDateTime.parse(rs.getString("start_time"))
                ));
            }
        }
        return entries;
    }

    public List<ScreenTimeEntry> getScreenTimeEntriesByUserIdAndDate(int userId, LocalDate date) throws SQLException {
        List<ScreenTimeEntry> entries = new ArrayList<>();
        String query = "SELECT application_name, SUM(duration) as total_duration " +
                "FROM screen_time_entries " +
                "WHERE user_id = ? AND date(start_time) = ? " +
                "GROUP BY application_name";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, date.toString()); // Ensure date is in 'yyyy-MM-dd' format
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                entries.add(new ScreenTimeEntry(
                        userId,
                        rs.getString("application_name"),
                        rs.getLong("total_duration"),
                        date.atStartOfDay()
                ));
            }
        }
        return entries;
    }



}