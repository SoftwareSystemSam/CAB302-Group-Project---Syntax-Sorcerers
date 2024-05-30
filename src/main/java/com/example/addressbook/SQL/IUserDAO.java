package com.example.addressbook.SQL;

import java.sql.SQLException;

/**
 * Interface for the User Data Access Object that handles
 * the CRUD operations for the User class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    public void addUser(User user);

    /**
     * Deletes a user from the database.
     * @param user The user to delete.
     */
    public void deleteUser(User user);
    /**
     * Retrieves a user from the database.
     *
     * @param id The id of the contact to retrieve.
     * @return The user with the given id, or null if not found.
     */

    public User getUser(int id);

    /**
     * Retrieves a user from the database by email.
     *
     * @param email The email of the contact to retrieve.
     * @return The user with the given email, or null if not found.
     */
    public User getUserByEmail(String email);

    /**
     * Updates a user in the database.
     * @param user The user to update.
     * @param newPassword The new password
     */
    void updateUser(User user, String newPassword);//For Update user Password

    /**
     * Updates a users custom notification in the database.
     * @param userId The id of the user
     * @param notification The custom notification
     * @throws SQLException If an SQL exception occurs
     */
    void setCustomNotification(int userId, String notification) throws SQLException;

    /**
     * Enable or disable custom notification
     * @param userId The id of the user
     * @param enabled The custom notification
     * @throws SQLException If an SQL exception occurs
     */
    void enableOrDisableCustomNotification(int userId, boolean enabled) throws SQLException;

    /**
        * Set the custom notification time by user id
        * @param userId The id of the user
        * @param minutes The custom notification time
        * @throws SQLException If an SQL exception occurs
        */
    void setCustomNotificationTime(int userId, int minutes) throws SQLException;

    /**
        * Set the screen time limit by user id
        * @param userId The id of the user
        * @param minutes The screen time limit
        * @throws SQLException If an SQL exception occurs
        */
    void setScreenTimeLimit(int userId, int minutes) throws SQLException;
    // Shouldn't be needed but leaving here just in case
    //User validateUser(String email, String password);

    /**
     * Get the boolean of if notification is enabled by user id
     * @param userId The id of the user
     * @return The custom notification
     * @throws SQLException If an SQL exception occurs
     */
    Boolean getUserNotificationEnabled(int userId) throws SQLException;

    /**
    * Get the screen time limit by user id
    * @param userId The id of the user
    * @return The screen time limit
    * @throws SQLException If an SQL exception occurs
    */
    int getScreenTimeLimit(int userId) throws SQLException;

    /**
    * Get the custom notification message by user id
    * @param userId The id of the user
    * @return The custom notification message
    * @throws SQLException If an SQL exception occurs
    */
    String getCustomNotificationMessage(int userId) throws SQLException;

    /**
     * Get the custom notification time by user id
     * @param userId The id of the user
     * @return The custom notification time
     * @throws SQLException If an SQL exception occurs
     */
    int getCustomNotificationTime(int userId) throws SQLException;



}