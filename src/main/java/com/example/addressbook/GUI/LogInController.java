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
 * The LogInController class extends Application and represents the login controller of the application.
 * It contains two buttons: "loginButton", and "signupButton".
 * Each button is associated with an event handler that handles the button click event.
 */
public class LogInController extends Application {

    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;

    /**
     * The start method of the LogInController class.
     * It initializes the login scene with the specified width and height, and sets the title of the stage.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The onLogInButtonClick method of the LogInController class.
     * It handles the click event of the login button, and opens the LogIn scene.
     */
    @FXML
    protected void onLogInButtonClick() throws IOException {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            LogIn graphsWindow = new LogIn();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * The onSignUpButtonClick method of the LogInController class.
     * It handles the click event of the signup button, and opens the SignUp scene.
     */
    @FXML
    protected void onSignUpButtonClick() throws IOException {
        try {
            Stage stage = (Stage) signupButton.getScene().getWindow();
            SignUp graphsWindow = new SignUp();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}