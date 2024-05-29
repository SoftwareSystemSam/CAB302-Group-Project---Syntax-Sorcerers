package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * MyNotifications class handles the user notifications settings.
 */
public class MyNotifications extends Application {

    @FXML private CheckBox enableNotifications;
    @FXML private TextField notificationMessage;
    @FXML private Slider notificationFrequency;
    @FXML private Button saveSettingsButton;
    @FXML private Label frequencyValue;

    @FXML private TextField screenTimeLimitHours;
    @FXML private TextField screenTimeLimitMinutes;
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

        // Add a listener to the slider to update the label dynamically
        // https://stackoverflow.com/questions/26854301/javafx-slider-value-update
        notificationFrequency.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                frequencyValue.setText(String.format("%.0f", newValue));
            }
        });
    }

    /**
     * Handles the submit action to save settings.
     */
    @FXML
    private void handleSubmit() {
        try {
            boolean isEnabled = enableNotifications.isSelected();
            String message = notificationMessage.getText();
            int frequency = (int) notificationFrequency.getValue();
            int hours = Integer.parseInt(screenTimeLimitHours.getText().isEmpty() ? "0" : screenTimeLimitHours.getText());
            int minutes = Integer.parseInt(screenTimeLimitMinutes.getText().isEmpty() ? "0" : screenTimeLimitMinutes.getText());
            int totalMinutes = hours * 60 + minutes;

            screenTimeEntryDAO.enableOrDisableCustomNotification(currentUser.getId(), isEnabled);
            if (isEnabled) {
                screenTimeEntryDAO.setCustomNotification(currentUser.getId(), message);
                screenTimeEntryDAO.setCustomNotificationTime(currentUser.getId(), frequency);
            }
            screenTimeEntryDAO.setScreenTimeLimit(currentUser.getId(), totalMinutes);

            // Additional logic to handle notifications
        } catch (SQLException e) {
            e.printStackTrace(); // Ideally, show a user-friendly error message
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Handle case where the input is not a valid integer
        }
    }
}
