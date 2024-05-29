package com.example.addressbook.SQL;

import java.sql.*;

/**
 * This class is used to handle the User DAO
 */
public class SqliteUserDAO implements IUserDAO {
    private Connection connection;



    /**
     * Constructor for the SqliteUserDAO
     * @param connection The connection to the database
    * */
    public SqliteUserDAO(Connection connection) {
        this.connection = connection;
        createTable();

    }

    /**
     * Create the User table if it doesn't exist
     */

    public void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "email VARCHAR NOT NULL UNIQUE,"
                    + "password VARCHAR NOT NULL,"
                    + "custom_notification_enabled BOOLEAN default false,"
                    + "screen_time_limit_minutes INTEGER default 60,"
                    + "custom_notification_time_minutes INTEGER default 60,"
                    + "custom_notification_message VARCHAR default 'You have been using your computer for a long time'"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a user to the database
     * @param user The user to be added
     * */
    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email,password) VALUES (?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2,user.getPassword());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Delete a user from the database
     * @param user  The user to be deleted
     * */
    @java.lang.Override
    public void deleteUser(User user) {
        try (
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();

        }
    }


    /**
     * Get a user from the database by their ID
     * @param id The ID of the user to be retrieved
     * @return The user with the given ID
     */

    @java.lang.Override
    public User getUser(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming User class has a constructor like User(id, email, password).
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("password"),
                            resultSet.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Get a user from the database by their email
     * @param email The email of the user to be retrieved
     * @return The user with the given email
     */
    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("password"), // Ideally should be a hashed password
                        resultSet.getString("email")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update a user in the database
     * @param user The user to be updated
     * */
    @Override
    public void updateUser(User user, String newPassword) {
        String updateSQL = "UPDATE users SET password = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getUserNotificationEnabled(int userId) throws SQLException {
        String query = "SELECT custom_notification_enabled FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("custom_notification_enabled");
            }
        }
        return false;
    }

    /**
     * Set the custom notification by user id
     *
     * @param userId The id of the user
     * @param notification The custom notification
     * @throws SQLException If an SQL exception occurs
     */

    public void setCustomNotification(int userId, String notification) throws SQLException {
        String updateQuery = "UPDATE users SET custom_notification_message = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, notification);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }
    /**
     * Get the total screen time by user id
     *
     * @param userId The id of the user
     * @return The total screen time
     * @throws SQLException If an SQL exception occurs
     */
    public void enableOrDisableCustomNotification(int userId, boolean enabled) throws SQLException {
        String updateQuery = "UPDATE users SET custom_notification_enabled = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setBoolean(1, enabled);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Get the custom notification by user id
     *
     * @param userId The id of the user
     * @return The custom notification
     * @throws SQLException If an SQL exception occurs
     */
    public void setCustomNotificationTime(int userId, int minutes) throws SQLException {
        String updateQuery = "UPDATE users SET custom_notification_time_minutes = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, minutes);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }
    /**
     * Get the screen time limit by user id
     *
     * @param userId The id of the user
     * @return The screen time limit
     * @throws SQLException If an SQL exception occurs
     */
    public void setScreenTimeLimit(int userId, int minutes) throws SQLException {
        String updateQuery = "UPDATE users SET screen_time_limit_minutes = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, minutes);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }

    public int getScreenTimeLimit(int userId) throws SQLException {
        String query = "SELECT screen_time_limit FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("screen_time_limit");
            }
        }
        return 0;
    }

    public String getCustomNotificationMessage(int userId) throws SQLException {
        String query = "SELECT custom_notification_message FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("custom_notification_message");
            }
        }
        return null;
    }
}

    // The below code shouldn't be needed because the User data will be yoinked via the getUserByEmail, then you can compare user.password to inputPassword

//    public User validateUser(String email, String password) {
//        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
//            statement.setString(1, email);
//            statement.setString(2, password); // Keeps password as plaintext which is sussy
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return new User(
//                        resultSet.getInt("id"),
//                        resultSet.getString("email"),
//                        resultSet.getString("password")
//
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


