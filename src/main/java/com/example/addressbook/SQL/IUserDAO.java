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

    public User getUserByEmail(String email);

    // Shouldn't be needed but leaving here just in case
    //User validateUser(String email, String password);

}