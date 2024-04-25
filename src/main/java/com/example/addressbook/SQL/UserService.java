package com.example.addressbook.SQL;

public class UserService {

    private IUserDAO userDAO;

    public UserService(IUserDAO userDAO){
        this.userDAO= userDAO;
    }

    public void registerNewUser(String email, String password){
        User newUser = new User(email,password);
        userDAO.addUser(newUser);
    }

    public User findUserById(int id){
        return userDAO.getUser(id);
    }

    public void deleteUser(int id){
        User user = userDAO.getUser(id);
        if(user!= null){
            userDAO.deleteUser(user);
        }
    }
}
