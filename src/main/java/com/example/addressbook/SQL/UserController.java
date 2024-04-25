package com.example.addressbook.SQL;

public class UserController {

    private UserService userService;

    public UserController(){
        this.userService = new UserService((new SqliteUserDAO()));
    }
}
