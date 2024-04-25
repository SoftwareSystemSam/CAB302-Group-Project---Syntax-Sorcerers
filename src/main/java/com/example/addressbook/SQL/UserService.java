package com.example.addressbook.SQL;

import java.util.Objects;

public class UserService {

    private IUserDAO userDAO;

    public UserService(IUserDAO userDAO){
        this.userDAO= userDAO;
    }

    public boolean registerNewUser(String email, String password){
        if(userDAO.getUserByEmail(email) != null){
            // User already exists
            return false;
        }
        else{
            User newUser = new User(email,password);
            userDAO.addUser(newUser);
            return true;
        }
    }

    public User loginUser(String email, String password){
        User user = userDAO.getUserByEmail(email);
        if( user!= null && Objects.equals(password, user.getPassword())){
            // If password matches then the login is sucessful
            return user;
        }
        else{
            //login failed
            return null;
        }
    }


    // This function may not be needed. Leaving here just in case
    public User findUserById(int id){
        return userDAO.getUser(id);
    }


    // This will be needed later if a user wants to delete themselves from the database.
    public void deleteUser(int id){
        User user = userDAO.getUser(id);
        if(user!= null){
            userDAO.deleteUser(user);
        }
    }
}
