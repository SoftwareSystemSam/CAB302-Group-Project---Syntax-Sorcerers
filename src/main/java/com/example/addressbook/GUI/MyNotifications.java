package com.example.addressbook.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;

/**
 * MyNotifications class handles the user notifications settings.
 */
public class MyNotifications extends Application {
    @FXML
    private CheckBox Goal1;
    @FXML
    private CheckBox Goal2;
    @FXML
    private CheckBox Goal3;
    @FXML
    private CheckBox Goal4;
    @FXML
    private CheckBox Goal5;
    /**
     * Starts the MyNotifications stage.
     * @param primaryStage The primary stage.
     * @throws Exception If an exception occurred.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/addressbook/MyNotifications.fxml")));
        primaryStage.setTitle("MyNotifications");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    /**
     * Handles the submit action.
     */
    @FXML
    private void handleSubmit() {

    }
}
