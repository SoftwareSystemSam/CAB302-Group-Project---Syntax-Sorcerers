package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.IUserDAO;
import com.example.addressbook.SQL.SqliteConnection;
import com.example.addressbook.SQL.SqliteUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import com.example.addressbook.SQL.UserService;
import net.bytebuddy.agent.VirtualMachine;
/**
 * This class is used to handle the sign up controller
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
     * This function is used to start the sign up controller
     * */
    public SignUp() {
        initializeUserService();
    }

    /**
     * This function is used to start the sign up controller
     * @param stage The stage to start
     * @throws IOException If an exception occurs
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
     * This function is used to initialize the user service
     */
    private void initializeUserService() {
        Connection userConnection = SqliteConnection.getUserDbInstance();
        IUserDAO userDAO = new SqliteUserDAO(userConnection);
        this.userService = new UserService(userDAO, userConnection);
    }



//    @FXML
//    protected void signupButtonAction() throws IOException {
//        //get text
//        String email = emailTextField.getText();
//        String password = passwordTextField.getText();
//
//        // Output the text written in the text box to the console.
//        System.out.println("Email: " + email);
//        System.out.println("Password: " + password);
//
//        boolean success = userService.registerNewUser(email, password);
//
//        // Handle navigation back to the login page
//        Stage stage = (Stage) signupButton.getScene().getWindow();
//        MyHubController loginWindow = new MyHubController();
//        loginWindow.start(stage);
//    }

    /**
     * This function is used to handle the sign up button click
     * @throws IOException If an IO exception occurs
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
     * This function is used to show the alert
     * @param message The message to show
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
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