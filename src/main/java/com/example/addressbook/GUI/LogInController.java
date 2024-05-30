package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.addressbook.HelloApplication.TITLE;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
/**
 * This class is used to handle the login controller
 */
public class LogInController extends Application {

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
     * The login button
     */
    @FXML
    private Button loginButton;
    /**
     * The sign up button
     */
    @FXML
    private Button signupButton;

    /**
     * This function is used to start the login controller
     * @param stage The stage to start
     * @throws Exception If an exception occurs
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
     * This function is used to handle the login button click
     * @throws IOException If an IO exception occurs
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
     * This function is used to handle the sign up button click
     * @throws IOException If an IO exception occurs
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