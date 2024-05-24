package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.IUserDAO;
import com.example.addressbook.SQL.SqliteConnection;
import com.example.addressbook.SQL.SqliteUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;

import com.example.addressbook.SQL.UserService;

/**
 * The SignUp class extends Application and represents the sign-up controller of the application.
 * It contains two buttons: "signupButton", and "backButton".
 * Each button is associated with an event handler that handles the button click event.
 */
public class SignUp {
    @FXML
    private Button signupButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    private UserService userService;

    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    /**
     * Constructor for SignUp class.
     * Initializes the UserService.
     */
    public SignUp() {
        initializeUserService();
    }
    /**
     * Starts the SignUp stage.
     * @param stage The stage to start.
     * @throws IOException If an input or output exception occurred.
     */
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signUp-view.fxml"));
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
     * Handles the signup button action.
     * @throws Exception If an exception occurred.
     */
    @FXML
    protected void signupButtonAction() throws Exception {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Email and password cannot be empty.");
            return;
        }

        if (password.length() < 8) { // Example: Check minimum password length
            showAlert("Password must be at least 8 characters long.");
            return;
        }

        boolean success = userService.registerNewUser(email, password);

        if (success) {
            // Handle successful signup
            Stage stage = (Stage) signupButton.getScene().getWindow();
            LogInController loginWindow = new LogInController();
            loginWindow.start(stage);
        } else {
            // Handle failed signup
            showAlert("Signup failed. Email already exists or other issues.");
        }
    }


    /**
     * Shows an alert with the given message.
     * @param message The message to show in the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
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
}