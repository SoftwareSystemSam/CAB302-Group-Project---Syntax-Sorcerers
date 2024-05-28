package com.example.addressbook.SQL;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to handle the screen time entry data access object
 */
public class SqliteScreenTimeEntryDAO implements IScreenTimeEntryDAO {
    // Database connection and table

    private Connection connection;
    /**
     * Constructor for the SqliteScreenTimeEntryDAO
     * @param connection The connection to the database
     */
    public SqliteScreenTimeEntryDAO(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.connection = connection;
        createTable();
        createGoalsTable();
    }

    /**
     * Create the Screen Time Entry table if it doesn't exist
     */
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

    private void createGoalsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS goals ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "user_id INTEGER,"
                    + "goal VARCHAR,"
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a screen time entry to the database
     * @param entry The screen time entry to be added
     * @throws SQLException If an SQL exception occurs
     */
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
    }// Testing
    /**
     * Get all screen time entries by user id
     * @param userId The user id
     * @return A list of screen time entries
     * @throws SQLException If an SQL exception occurs
     */
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
    /**
     * Get all screen time entries by user id and date
     * @param userId The user id
     * @param date The date
     * @return A list of screen time entries
     * @throws SQLException If an SQL exception occurs
     */
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
    public Map<String, ScreenTimeEntry> getMostUsedAppForEachDayOfWeek(int userId) throws SQLException {
        Map<String, ScreenTimeEntry> mostUsedApps = new HashMap<>();
        String query = "SELECT strftime('%w', start_time) as day_of_week, application_name, SUM(duration) as total_duration " +
                "FROM screen_time_entries " +
                "WHERE user_id = ? " +
                "GROUP BY day_of_week, application_name " +
                "ORDER BY day_of_week, total_duration DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            String currentDayOfWeek = null;
            while (rs.next()) {
                String dayOfWeek = rs.getString("day_of_week");
                if (!dayOfWeek.equals(currentDayOfWeek)) {
                    currentDayOfWeek = dayOfWeek;
                    mostUsedApps.put(dayOfWeek, new ScreenTimeEntry(
                            userId,
                            rs.getString("application_name"),
                            rs.getLong("total_duration"),
                            null
                    ));
                }
            }
        }
        return mostUsedApps;
    }

  
    /**
     * Find the most recent start time by user id, application name, and date
     * @param userId The user id
     * @param applicationName The application name
     * @param date The date
     * @return The most recent start time
     * @throws SQLException If an SQL exception occurs
     */
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
    /**
     * Upsert a screen time entry
     * @param userId The user id
     * @param applicationName The application name
     * @param duration The duration
     * @param dateTime The date and time
     * @throws SQLException If an SQL exception occurs
     */
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
    /**
     * Get the weekly screen time by user id
     * @param userId The user id
     * @param startOfWeek The start of the week
     * @return A map of the weekly screen time
     * @throws SQLException If an SQL exception occurs
     */
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

   public void addGoal(int userId, String goal) throws SQLException {
        String insertQuery = "INSERT INTO goals (user_id, goal) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, goal);
            pstmt.executeUpdate();
        }
    }

    public void deleteGoal(int userId, String goal) throws SQLException {
        String deleteQuery = "DELETE FROM goals WHERE user_id = ? AND goal = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, goal);
            pstmt.executeUpdate();
        }
    }
}