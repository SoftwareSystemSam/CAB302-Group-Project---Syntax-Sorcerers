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




public class ResetPass{
    @FXML
    private Button resetButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordField;
    private UserService userService;


    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    public ResetPass() {
        initializeUserService();
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("resetpass-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }



    private void initializeUserService() {
        Connection userConnection = SqliteConnection.getUserDbInstance();
        IUserDAO userDAO = new SqliteUserDAO(userConnection);
        this.userService = new UserService(userDAO, userConnection);
    }

    private void showDatabaseError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Failed to connect to the database.");
        alert.show();
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
    protected void onReset() throws IOException {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        try {

            User  authenticatedUser = userService.resetUserPassword(email, password);

            if (authenticatedUser != null) {
                // login success


                //Start up the database for the window tracking
                Connection screenTimeConnection = SqliteConnection.getScreenTimeDbInstance();
                IScreenTimeEntryDAO screenDAO = new SqliteScreenTimeEntryDAO(screenTimeConnection);
                // Activate Window Tracker - Will need to create a thread for this, otherwise program will hang
                //https://www.geeksforgeeks.org/runnable-interface-in-java/
                ActiveWindowTracker tracker = new ActiveWindowTracker(authenticatedUser, screenTimeConnection);
                Thread trackerThread = new Thread(tracker);
                trackerThread.start(); // Start tracking in a new thread



                //  Go to my hub
                Stage stage = (Stage) resetButton.getScene().getWindow();
                MyHubController graphsWindow = new MyHubController(authenticatedUser, screenDAO);
                graphsWindow.start(stage);
            } else {
                showLoginFailedAlert();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDatabaseError();
        }
    }

    private void showLoginFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Login failed. The email address or password is incorrect.");
        alert.showAndWait();
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

