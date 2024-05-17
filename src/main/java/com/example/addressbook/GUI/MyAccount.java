package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MyAccount extends Application {

    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button confirmButton;
    @FXML
    private Button backButton;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account.fxml"));
        fxmlLoader.setController(this); // Set the controller
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

        // Disable the confirm button initially
        confirmButton.setDisable(true);

        // Add listener to the email text field to enable the confirm button when the email is verified
        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the email is valid (you can implement your email verification logic here)
            boolean isEmailValid = isValidEmail(newValue);
            // Enable or disable the confirm button based on email validity
            confirmButton.setDisable(!isEmailValid);
        });
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

    // Method to check if the entered email is valid
    private boolean isValidEmail(String email) {
        // Implement your email validation logic here
        // For simplicity, let's assume any non-empty email is valid
        return email != null && !email.trim().isEmpty();
    }

    // Define action for the confirm button
    @FXML
    private void confirmButtonAction() {
        // Implement your logic for handling password change confirmation here
        System.out.println("Password change confirmed!");
    }

}
