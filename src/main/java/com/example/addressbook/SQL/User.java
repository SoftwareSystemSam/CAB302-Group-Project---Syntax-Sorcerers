package com.example.addressbook.SQL;
/**
 * This class is used to handle the user
 */
public class User {
    private int id;
    private String email;
    private String password;


    /**
     * Constructor for the User
     * @param password The password of the user
     * @param email The email of the user
     */
    public User(String password, String email) {

        this.email = email;
        this.password = password;
    }

    /**
     *  Overloaded constructor for the User
     * @param id The id of the user
     * @param password The password of the user
     * @param email The email of the user
     */
    public User(int id, String password, String email) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Get the id of the user
     * @return The id of the user
     */
    public int getId() {
        return id;
    }
    /**
     * Set the id of the user
     * @param id The id of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the email of the user
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }
    /**
     * Set the email of the user
     * @param email The email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Get the password of the user
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }
    /**
     * Set the password of the user
     * @param password The password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}