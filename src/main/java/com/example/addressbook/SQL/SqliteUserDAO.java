package com.example.addressbook.SQL;

import java.sql.*;


public class SqliteUserDAO implements IUserDAO {
    private Connection connection;

    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
        createTable();

    }



    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "email VARCHAR NOT NULL UNIQUE,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email,password) VALUES (?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2,user.getPassword());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @java.lang.Override
    public void deleteUser(User user) {
        try (
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();

        }
    }



    @java.lang.Override
    public User getUser(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming User class has a constructor like User(id, email, password).
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password") // Ideally should be a hashed password
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // The below code shouldn't be needed because the User data will be yoinked via the getUserByEmail, then you can compare user.password to inputPassword

//    public User validateUser(String email, String password) {
//        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
//            statement.setString(1, email);
//            statement.setString(2, password); // Keeps password as plaintext which is sussy
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return new User(
//                        resultSet.getInt("id"),
//                        resultSet.getString("email"),
//                        resultSet.getString("password")
//
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

