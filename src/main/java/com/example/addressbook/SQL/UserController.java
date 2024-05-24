package com.example.addressbook.SQL;

import java.sql.Connection;

/**
 * This class is used to handle the user service
 */
public class UserController {
    private UserService userService;
    private Connection connection;


    public UserController(){
        this.connection = SqliteConnection.getUserDbInstance();
        IUserDAO userDao = new SqliteUserDAO(connection);
        this.userService = new UserService(userDao,connection);
    }
}
