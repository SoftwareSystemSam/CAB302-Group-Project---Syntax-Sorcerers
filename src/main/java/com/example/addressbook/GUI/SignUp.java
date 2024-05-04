package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.IUserDAO;
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


    public SignUp() {
        initializeUserService();
    }

    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signUp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeUserService() {
        try {
            // Assuming you have a method to get a connection
            Connection connection = DriverManager.getConnection("jdbc:sqlite:path_to_your_database.db");
            IUserDAO userDAO = new SqliteUserDAO(connection);
            this.userService = new UserService(userDAO,connection);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to connect to the database.");
            alert.show();
        }
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



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
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
}