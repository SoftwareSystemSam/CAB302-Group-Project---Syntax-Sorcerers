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
 * This class is used to handle the login controller
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
     * This function is used to start the login controller
     * */
    public LogIn() {
        initializeUserService();
    }

    /**
     * This function is used to start the login controller
     * @param stage The stage to start
     * @throws IOException If an exception occurs
     */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
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
     * This function is used to show the database error
     */
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

    /**
     * This function is used to handle the login button click
     * @throws IOException If an IO exception occurs
     */
    @FXML
    protected void onLogIn() throws IOException {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        try {

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
     * This function is used to show the login failed alert
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

//    /**
//     * This function is used to handle the reset button click
//     * @throws IOException If an IO exception occurs
//     */
//    @FXML
//    protected void onReset() throws IOException {
//        try {
//            Stage stage = (Stage) resetButton.getScene().getWindow();
//            ResetPass graphsWindow = new ResetPass();
//            graphsWindow.start(stage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

