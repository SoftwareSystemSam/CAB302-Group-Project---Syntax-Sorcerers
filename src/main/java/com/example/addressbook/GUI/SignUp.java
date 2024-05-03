package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
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
    private Button signupButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordField;

    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;


    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signUp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void signupButtonAction() throws IOException {
        // Handle navigation back to the login page
        Stage stage = (Stage) signupButton.getScene().getWindow();
        MyHubController loginWindow = new MyHubController();
        loginWindow.start(stage);
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

    @FXML
    protected void GetEmailAction() throws IOException {
        // Handle navigation back to the login page
        Stage stage = (Stage) emailTextField.getScene().getWindow();
    }
    @FXML
    protected void GetPasswordAction() throws IOException {
        // Handle navigation back to the login page
        Stage stage = (Stage) passwordField.getScene().getWindow();
    }
}