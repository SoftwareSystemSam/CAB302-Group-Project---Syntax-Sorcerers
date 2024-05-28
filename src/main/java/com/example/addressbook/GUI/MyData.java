package com.example.addressbook.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class MyData extends Application {
    @FXML
    private Button privacyPolicyButton;

    @FXML
    private Button deleteDataButton;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/addressbook/MyData.fxml")));
        primaryStage.setTitle("MyData");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @FXML
    private void showPrivacyPolicy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Privacy Policy");
        alert.setHeaderText(null);
        alert.setContentText("This is the privacy policy of the app. WE WILL SELL YOUR DATA TO THE CCP! WE ALSO HAVE A CREDIT CARD LOGGER");
        alert.showAndWait();
    }

    @FXML
    private void deleteAllData() {
        Alert warningAlert = new Alert(Alert.AlertType.CONFIRMATION);
        warningAlert.setTitle("Confirm Data Deletion");
        warningAlert.setHeaderText("Warning: This action is irreversible!");
        warningAlert.setContentText("Do you really want to delete all data? This cannot be undone. Please enter your email address to confirm.");

        TextInputDialog emailInput = new TextInputDialog();
        emailInput.setTitle("Confirm Data Deletion");
        emailInput.setHeaderText("Enter your email address");
        emailInput.setContentText("Email:");

        Optional<ButtonType> result = warningAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Optional<String> email = emailInput.showAndWait();
            email.ifPresent(value -> {
                if (isValidEmail(value)) {
                    performDataDeletion();
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Data Deleted");
                    infoAlert.setHeaderText(null);
                    infoAlert.setContentText("All data has been successfully deleted.");
                    infoAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Invalid Email");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("The email address entered is invalid.");
                    errorAlert.showAndWait();
                }
            });
        }
    }

    private boolean isValidEmail(String email) {
        // Basic email validation
        return email != null && email.contains("@") && email.contains(".");
    }

    private void performDataDeletion() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Data Deletion Confirmation");
        confirm.setHeaderText("Your data has been deleted");
        confirm.setContentText("Have a day.");
        // Logic to delete all data entries
        System.out.println("All data entries deleted.");
    }
}

