package com.example.addressbook.SQL;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
/**
 * This class is used to handle the screen time entry data access object
 */
public interface IScreenTimeEntryDAO{
    /**
     * Add a screen time entry to the database
     * @param entry The screen time entry to be added
     * @throws SQLException If an SQL exception occurs
     */
    void addScreenTimeEntry(ScreenTimeEntry entry) throws SQLException;
    /**
     * Get all screen time entries
     * @return A list of all screen time entries
     * @throws SQLException If an SQL exception occurs
     */
    List<ScreenTimeEntry> getScreenTimeEntriesByUserId(int userId) throws SQLException;
    /**
     * Get all screen time entries by user id and date
     * @param userId The id of the user
     * @param date The date of the screen time entries
     * @return A list of screen time entries
     * @throws SQLException If an SQL exception occurs
     */

    List<ScreenTimeEntry> getScreenTimeEntriesByUserIdAndDate(int userId, LocalDate date) throws SQLException;
    Map<String, ScreenTimeEntry> getMostUsedAppForEachDayOfWeek(int userId) throws SQLException;
    /**
     * Add or update a screen time entry
     * @param userId The id of the user
     * @param applicationName The name of the application
     * @param duration The duration of the screen time entry
     * @param dateTime The date and time of the screen time entry
     * @throws SQLException If an SQL exception occurs
     */

    void upsertScreenTimeEntry(int userId, String applicationName, long duration, LocalDateTime dateTime) throws SQLException;
    /**
     * Get the weekly screen time by user id
     * @param userId The id of the user
     * @param startOfWeek The start of the week
     * @return A map of the day of the week and the total screen time
     * @throws SQLException If an SQL exception occurs
     */

    Map<DayOfWeek, Long> getWeeklyScreenTimeByUserId(int userId, LocalDate startOfWeek) throws SQLException;


    /**
     * Get the most recent start time by user id, application name, and date
     * @param userId The id of the user
     * @param applicationName The name of the application
     * @param date The date of the screen time entry
     * @return The most recent start time
     * @throws SQLException If an SQL exception occurs
     */

    LocalDateTime findMostRecentStartTimeByUserAppAndDate(int userId, String applicationName, LocalDate date) throws SQLException;
    // TODO add aditional methods like getScreenTimeEntries for graphs etc
}