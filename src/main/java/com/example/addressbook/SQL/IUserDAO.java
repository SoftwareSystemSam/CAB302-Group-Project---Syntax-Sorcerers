package com.example.addressbook.SQL;

import java.util.List;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new contact to the database.
     * @param contact The contact to add.
     */
    public void addUser(User user);

    /**
     * Deletes a contact from the database.
     * @param contact The contact to delete.
     */
    public void deleteUser(User user);
    /**
     * Retrieves a contact from the database.
     * @param id The id of the contact to retrieve.
     * @return The contact with the given id, or null if not found.
     */

    publc void GetUser(int id);

}