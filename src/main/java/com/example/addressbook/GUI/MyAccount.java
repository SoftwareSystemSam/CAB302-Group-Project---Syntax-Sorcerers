package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * MyAccount class handles the user account settings.
 */
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
    /**
     * Starts the MyAccount stage.
     * @param stage The stage to start.
     * @throws IOException If an input or output exception occurred.
     */
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
     * Checks if the entered email is valid.
     * @param email The email to check.
     * @return true if the email is valid, false otherwise.
     */

    private boolean isValidEmail(String email) {
        // Implement your email validation logic here
        // For simplicity, let's assume any non-empty email is valid
        return email != null && !email.trim().isEmpty();
    }

    /**
     * Defines action for the confirm button.
     */
    @FXML
    private void confirmButtonAction() {
        // Implement your logic for handling password change confirmation here
        // Show success alert
        showAlert("Success", "An email has been sent, confirming your password change");
    }
    /**
     * Shows an information alert with the given title and message.
     * @param title The title of the alert.
     * @param message The message of the alert.
     */

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
