package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
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

    private User currentUser;

    private IScreenTimeEntryDAO screenTimeEntryDAO;

    public MyNotifications(User user, IScreenTimeEntryDAO screenDAO) {
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }

    /**
     * Starts the MyNotifications stage.
     * @param primaryStage The primary stage.
     * @throws Exception If an exception occurred.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/MyNotifications.fxml"));
        loader.setController(this);
        Parent root = loader.load();
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
