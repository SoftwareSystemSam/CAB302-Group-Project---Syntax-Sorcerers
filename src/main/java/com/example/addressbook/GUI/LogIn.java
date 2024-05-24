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
 * LogIn class handles the user login process.
 */
public class LogIn{
    @FXML
    private Button loginButton;
    @FXML
    private Button backButton;
    @FXML
    private Button accountButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordField;
     private UserService userService;


    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    /**
     * Constructor for LogIn class.
     * Initializes the UserService.
     */
    public LogIn() {
        initializeUserService();
    }
    /**
     * Starts the LogIn stage.
     * @param stage The stage to start.
     * @throws IOException If an input or output exception occurred.
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Initializes the UserService.
     */
    private void initializeUserService() {
        Connection userConnection = SqliteConnection.getUserDbInstance();
        IUserDAO userDAO = new SqliteUserDAO(userConnection);
        this.userService = new UserService(userDAO, userConnection);
    }
    /**
     * Shows an error alert when database connection fails.
     */
    private void showDatabaseError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Failed to connect to the database.");
        alert.show();
    }

    /**
     * Handles the login button action.
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    protected void onLogIn() throws IOException {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        try {
            // ユーザー認証を行う
            User  authenticatedUser = userService.loginUser(email, password);

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
                Stage stage = (Stage) loginButton.getScene().getWindow();
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
    /**
     * Shows an alert when login fails.
     */
    private void showLoginFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Login failed. The email address or password is incorrect.");
        alert.showAndWait();
    }
    /**
     * Handles the account button action.
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    protected void myAccount() throws IOException {
        try {
            Stage stage = (Stage) accountButton.getScene().getWindow();
            MyAccount myAccount = new MyAccount();
            myAccount.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the back button action.
     * @throws IOException If an input or output exception occurred.
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
    /**
     * Handles the email text field action.
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    protected void GetEmailAction() throws IOException {
        // Handle navigation back to the login page
        Stage stage = (Stage) emailTextField.getScene().getWindow();
    }
    /**
     * Handles the password field action.
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    protected void GetPasswordAction() throws IOException {
        // Handle navigation back to the login page
        Stage stage = (Stage) passwordField.getScene().getWindow();
    }
}

