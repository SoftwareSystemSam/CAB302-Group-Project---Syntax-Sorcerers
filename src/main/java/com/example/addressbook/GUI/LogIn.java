package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

//import com.example.addressbook.SQL.UserDAO;
import com.example.addressbook.SQL.IUserDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import com.example.addressbook.SQL.UserService;


public class LogIn {
    @FXML
    private Button loginButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordField;
    private IUserDAO userDAO;
    private Connection connection;
    private UserService userService;


    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

//        @FXML  before changes
//    protected void onLogIn() throws IOException {
//        try {
//            Stage stage = (Stage) loginButton.getScene().getWindow();
//            MyHubController graphsWindow = new MyHubController();
//            graphsWindow.start(stage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    protected void onLogIn() throws IOException {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        try {
            User user = userService.loginUser(email, password);
            if (user != null && Objects.equals(password, user.getPassword())) {
                // login success
                Stage stage = (Stage) loginButton.getScene().getWindow();
                MyHubController graphsWindow = new MyHubController();
                graphsWindow.start(stage);
            } else {
                // login fail
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void onBack() throws IOException {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            LogInController graphsWindow = new LogInController();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void GetEmailAction() throws IOException {
        // Handle navigation back to the login page
        Stage stage = (Stage) emailTextField.getScene().getWindow();
    }

    @FXML
    protected void GetPasswordAction() throws IOException {
        // Handle navigation back to the login page
        Stage stage = (Stage) passwordField.getScene().getWindow();
    }
}



