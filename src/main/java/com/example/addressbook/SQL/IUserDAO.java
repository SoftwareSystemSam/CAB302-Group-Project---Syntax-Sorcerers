package com.example.addressbook.SQL;

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
     */

    void updateUser(User user, String newPassword);//For Update user Password

    // Shouldn't be needed but leaving here just in case
    //User validateUser(String email, String password);

}