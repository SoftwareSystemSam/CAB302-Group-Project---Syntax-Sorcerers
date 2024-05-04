package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class LogIn{
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

    public LogIn() {
        initializeUserService();
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeUserService() {
        try {
            // Assuming you have a method to get a connection
            Connection connection = DriverManager.getConnection("jdbc:sqlite:path_to_your_database.db");// Get path to DB
            IUserDAO userDAO = new SqliteUserDAO(connection);
            this.userService = new UserService(userDAO,connection);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to connect to the database.");
            alert.show();
        }
    }


    //    @FXML   before change
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
            // ユーザー認証を行う
            User  authenticatedUser = userService.loginUser(email, password);

            if (authenticatedUser != null) {
                // login success

                // Activate Window Tracker
                 ActiveWindowTracker tracker = new ActiveWindowTracker(authenticatedUser,connection);
                 tracker.trackActiveWindow(); // Now you can start tracking
                // Activate Window Tracker

                Stage stage = (Stage) loginButton.getScene().getWindow();
                MyHubController graphsWindow = new MyHubController();
                graphsWindow.start(stage);
            } else {
                // login fail
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Login failed. The email address or password is incorrect.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("In charge of interacting with the database.");
            alert.showAndWait();
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

