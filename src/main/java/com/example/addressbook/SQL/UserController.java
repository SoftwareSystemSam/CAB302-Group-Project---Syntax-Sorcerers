package com.example.addressbook.SQL;

// Basically a controller that lets UserService functions use the SqliteUserDAO database functions
public class UserController {

    public UserController(){
        UserService userService = new UserService((new SqliteUserDAO()));
    }
}
