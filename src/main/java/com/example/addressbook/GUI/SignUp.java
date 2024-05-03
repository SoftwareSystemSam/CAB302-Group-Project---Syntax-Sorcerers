package com.example.addressbook.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUp {
    @FXML
    private Button backButton;
    @FXML
    private TextField emailTextBox;
    @FXML
    private PasswordField passwordTextBox;


    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(emailTextBox, passwordTextBox, backButton);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setTitle("Sign up for an account");
        stage.setScene(scene);
        stage.show();
    }




    @FXML
    protected void onSignUp() {
        // Here you can handle the sign-up logic, e.g., validation, storing to database, etc.
        System.out.println("Sign-up information: Email=" + emailTextBox.getText() + " Password=" + passwordTextBox.getText());
        // Transition to the main application page or show success message
    }

    @FXML
    protected void onBack() {
        // Handle navigation back to the login page
        Stage stage = (Stage) backButton.getScene().getWindow();
        // Assuming LogIn is your main login window class
        LogIn logInWindow = new LogIn();
        logInWindow.start(stage);
    }
}