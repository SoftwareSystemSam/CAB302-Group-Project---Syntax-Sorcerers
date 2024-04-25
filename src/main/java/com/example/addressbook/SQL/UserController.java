package com.example.addressbook.SQL;

public class UserController {

    public UserController(){
        UserService userService = new UserService((new SqliteUserDAO()));
    }
}
