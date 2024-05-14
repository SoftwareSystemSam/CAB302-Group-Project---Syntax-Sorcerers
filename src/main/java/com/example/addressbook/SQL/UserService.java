package com.example.addressbook.SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class UserService {

    private IUserDAO userDAO;
    private Connection connection;

    public UserService(IUserDAO userDAO, Connection connection){
        this.userDAO= userDAO;
        this.connection = connection;
    }

    public boolean registerNewUser(String email, String password){
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return false; // Basic null/empty check
        }

        if (userDAO.getUserByEmail(email) != null) {
            // User already exists
            return false;
        } else {
            User newUser = new User(password, email);
            userDAO.addUser(newUser);
            return true;
        }
    }

    public User loginUser(String email, String password) throws SQLException {
        User user = userDAO.getUserByEmail(email);
        if( user!= null && Objects.equals(password, user.getPassword())){
            // I'm leaving this here because this needs to be done after authentication has been handled and probably shouldn't be done here.
            // Once User has been verified then you can start tracking because they should have an id in the database
            // ActiveWindowTracker tracker = new ActiveWindowTracker(user,connection);
            // tracker.trackActiveWindow(); // Now you can start tracking
            // If password matches then the login is sucessful
            return user;
        }
        else{
            //login failed
            return null;
        }
    }

    public User resetUserPassword(String email, String newPassword) throws SQLException {
        // Search for the user by email
        User user = userDAO.getUserByEmail(email);

        // Check if the user exists
        if (user != null) {
            // Set the new password
            user.setPassword(newPassword);

            // Update the user information
            userDAO.updateUser(user);

            // Return the updated user information
            return user;
        } else {
            // If the user does not exist, throw an error
            throw new IllegalArgumentException("No user found with the specified email address: " + email);
        }
    }

    // This function may not be needed. Leaving here just in case
    public User findUserById(int id){
        return userDAO.getUser(id);
    }


    // This will be needed later if a user wants to delete themselves from the database.
    public void deleteUser(int id){
        User user = userDAO.getUser(id);
        if(user!= null){
            userDAO.deleteUser(user);
        }
    }


}
