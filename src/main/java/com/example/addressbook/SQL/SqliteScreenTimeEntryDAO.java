package com.example.addressbook.SQL;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
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
            pstmt.setString(4, entry.getStartTime().toString());
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

  

    public LocalDateTime findMostRecentStartTimeByUserAppAndDate(int userId, String applicationName, LocalDate date) throws SQLException {
        String query = "SELECT start_time FROM screen_time_entries " +
                "WHERE user_id = ? AND application_name = ? AND date(start_time) = ? " +
                "ORDER BY start_time DESC LIMIT 1"; // Ensures only the most recent time is fetched
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, applicationName);
            pstmt.setString(3, date.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return LocalDateTime.parse(rs.getString("start_time"));
            }
        }
        return null;
    }

    // https://stackoverflow.com/questions/36442307/insert-data-and-if-already-inserted-then-update-in-sql
    public void upsertScreenTimeEntry(int userId, String applicationName, long duration, LocalDateTime dateTime) throws SQLException {
        LocalDate date = dateTime.toLocalDate(); // Extracting the date part for date checks
        LocalDateTime existingStartDateTime = findMostRecentStartTimeByUserAppAndDate(userId, applicationName, date);

        if (existingStartDateTime != null) {
            // Entry exists for today, update it
            String updateQuery = "UPDATE screen_time_entries SET duration = duration + ? WHERE user_id = ? AND application_name = ? AND start_time = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
                pstmt.setLong(1, duration);
                pstmt.setInt(2, userId);
                pstmt.setString(3, applicationName);
                pstmt.setString(4, existingStartDateTime.toString()); // Use the exact datetime of the existing entry
                pstmt.executeUpdate();
            }
        } else {
            // Entry does not exist for today, insert a new one with the current datetime
            String insertQuery = "INSERT INTO screen_time_entries (user_id, application_name, duration, start_time) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, applicationName);
                pstmt.setLong(3, duration);
                pstmt.setString(4, dateTime.toString()); // Use the actual current datetime
                pstmt.executeUpdate();
            }
        }
    }
    // https://www.w3schools.com/java/java_hashmap.asp <- little hashmap guide
    public Map<DayOfWeek, Long> getWeeklyScreenTimeByUserId(int userId, LocalDate startOfWeek) throws SQLException {
        Map<DayOfWeek, Long> weeklyScreenTime = new HashMap<>();
        String query = "SELECT date(start_time) as date, SUM(duration) as total_duration " +
                "FROM screen_time_entries " +
                "WHERE user_id = ? AND date(start_time) BETWEEN ? AND ? " +
                "GROUP BY date(start_time)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, startOfWeek.toString()); // start of the week
            pstmt.setString(3, startOfWeek.plusDays(6).toString()); // end of the week
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    Long duration = rs.getLong("total_duration");
                    weeklyScreenTime.put(date.getDayOfWeek(), duration);
                }
            }
        }
        return weeklyScreenTime;
    }





}