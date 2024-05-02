package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.addressbook.HelloApplication.TITLE;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

public class LogInController extends Application {


    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    @FXML
    private Button nextButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onNextButtonClick() throws IOException {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            LogIn graphsWindow = new LogIn();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onSignUpButtonClick() {
        try {
            Stage stage = (Stage) signupButton.getScene().getWindow();
            SignUp graphsWindow = new SignUp();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initUI(Stage stage) {
        // Create a VBox to hold the navigation bar and graphs
        var vbox = new VBox();
        var scene = new Scene(vbox, 950, 500);

        stage.setTitle("Log In and Register Bar");
        stage.setScene(scene);
        stage.show();
    }

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

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }


}