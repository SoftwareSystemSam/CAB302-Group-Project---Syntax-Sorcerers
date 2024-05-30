package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * This class is used to handle the reset password controller
 */
public class ResetPass{
    /**
     * The reset button
     */
    @FXML
    private Button resetButton;
    /**
     * The back button
     */
    @FXML
    private Button backButton;
    /**
     * The email text field
     */
    @FXML
    private TextField emailTextField;
    /**
     * The password text field
     */
    @FXML
    private TextField passwordField;
    /**
     * The user service
     */
    private UserService userService;
    /**
     * The title of the application window.
     */
    public static final String TITLE = "Screen Tracker";
    /**
     * The width of the application window.
     */
    public static final int WIDTH = 640;
    /**
     * The height of the application window.
     */
    public static final int HEIGHT = 360;

    /**
     * This function is used to start the reset password controller
     * */
    public ResetPass() {initializeUserService();}

    /**
     * This function is used to start the reset password controller
     * @param stage The stage to start
     * @throws IOException If an exception occurs
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("resetPass-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This function is used to initialize the user service
     */
    private void initializeUserService() {
        Connection userConnection = SqliteConnection.getUserDbInstance();
        IUserDAO userDAO = new SqliteUserDAO(userConnection);
        this.userService = new UserService(userDAO, userConnection);
    }
    /**
     * This function is used to handle the reset button click
     * @throws IOException If an IO exception occurs
     */
    private void showDatabaseError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Failed to connect to the database.");
        alert.show();
    }

    /**
     * This function is used to handle the reset button click
     * @throws IOException If an IO exception occurs
     */
    @FXML
    protected void onReset() throws IOException {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        // Validate input
        if ( email.isEmpty() || password.isEmpty()) {
            showAlert("Incorrect email or Password cannot be empty.");
            return;
        }

        if (password.length() < 8) { // Example: Check minimum password length
            showAlert("Password must be at least 8 characters long.");
            return;
        }

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
                MyHubController graphsWindow = new MyHubController(authenticatedUser, screenDAO, tracker, UserService.getUserDAO());
                graphsWindow.start(stage);
            } else {
                showLoginFailedAlert();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showDatabaseError();
        }
    }
    /**
     * This function is used to show the alert
     * @param message The message to show
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * This function is used to show the login failed alert
     */
    private void showLoginFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Reset Password is failed. The email address is incorrect.");
        alert.showAndWait();
    }
    /**
     * This function is used to handle the back button click
     * @throws IOException If an IO exception occurs
     */
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
}

