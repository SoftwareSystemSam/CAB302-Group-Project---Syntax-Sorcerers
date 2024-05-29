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

    /**
     * Add goal to the database by user id and goal
     * @param userId
     * @param goal
     * @throws SQLException
     */
    void addGoal(int userId, String goal) throws SQLException;

    /**
     * Delete a goal by user id and goal
     * @param userId The id of the user
     * @param goal The goal to be deleted
     * @throws SQLException If an SQL exception occurs
     *
     */
    void deleteGoal(int userId, String goal) throws SQLException;

    /**
     * Get all goals by user id
     * @param userId The id of the user
     * @return A list of goals
     * @throws SQLException If an SQL exception occurs
     */
    List<String> getGoalsByUserId(int userId) throws SQLException;

    /**
     * Delete all user data by user id
     * @param userId The id of the user
     * @throws SQLException If an SQL exception occurs
     */
    void deleteUserData(int userId) throws SQLException;

    /**
     * Delete all user data by user id within x days
     * @param userId The id of the user
     * @param days The number of days
     * @throws SQLException If an SQL exception occurs
     */
    void deleteUserDataWithinXDays(int userId, int days) throws SQLException;

    /**
     * Get the total screen time by user id to see if they have spent more than x minutes on the computer
     * @param userId The id of the user
     * @return The total screen time
     * @throws SQLException If an SQL exception occurs
     */
    Boolean hasUserSpentMoreThanXMinutesOnComputer(int userId, int hours) throws SQLException;
/**
     * Get the total screen time by user id to see if they have spent more than x hours on the computer
     * @param userId The id of the user
     * @return The total screen time
     * @throws SQLException If an SQL exception occurs
     */
    void setCustomNotification(int userId, String notification) throws SQLException;
    /**
     * Get the custom notification by user id
     * @param userId The id of the user
     * @return The custom notification
     * @throws SQLException If an SQL exception occurs
     */
    void enableOrDisableCustomNotification(int userId, boolean enabled) throws SQLException;

    /**
     * Get the custom notification time by user id
     * @param userId The id of the user
     * @return The custom notification time
     * @throws SQLException If an SQL exception occurs
     */
    void setCustomNotificationTime(int userId, int minutes) throws SQLException;

    /**
     * Get the screen time limit by user id
     * @param userId The id of the user
     * @return The screen time limit
     * @throws SQLException If an SQL exception occurs
     */
    void setScreenTimeLimit(int userId, int minutes) throws SQLException;


    // TODO add aditional methods like getScreenTimeEntries for graphs etc
}