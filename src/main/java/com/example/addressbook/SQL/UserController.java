package com.example.addressbook.SQL;

import java.sql.Connection;

// Basically a controller that lets UserService functions use the SqliteUserDAO database functions
public class UserController {
    private UserService userService;
    private Connection connection;


    public UserController(){
        this.connection = SqliteConnection.getUserDbInstance();
        IUserDAO userDao = new SqliteUserDAO(connection);
        this.userService = new UserService(userDao,connection);
    }
}
