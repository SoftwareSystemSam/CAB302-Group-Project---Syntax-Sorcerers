package com.example.addressbook.SQL;

public class User {
    private int id;
    private String email;
    private String password;


    // When creating new user, you won't know the id
    public User(String password, String email) {

        this.email = email;
        this.password = password;
    }

    // Overload constructor: when retrieving from database you will have id
    public User(int id, String password, String email) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}