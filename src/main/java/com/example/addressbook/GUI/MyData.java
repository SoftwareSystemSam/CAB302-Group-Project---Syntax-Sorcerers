package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.SqliteUserDAO;
import com.example.addressbook.SQL.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import java.sql.SQLException;
import java.util.Objects;
import javafx.fxml.FXML;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MyData class handles the user's data settings.
 */
public class MyData extends Application {
    /**
     * The privacy policy button.
     */
    @FXML
    private Button privacyPolicyButton;

    /** The current user. */
    private User currentUser;
    /**
     * The screen time entry DAO.
     */

    private IScreenTimeEntryDAO screenTimeEntryDAO;

    /**
     * The delete data button.
     */
    @FXML
    private Button deleteDataButton;

    /**
     * The time frame combo box.
     */
    @FXML
    private ComboBox<String> timeFrameComboBox;

    /**
     * The flag to ignore selection changes.
     */
    private boolean ignoreSelectionChanges = false;

    /**
     * Initializes the time frame combo box.
     */
    @FXML
    public void initialize() {
        assert timeFrameComboBox != null : "fx:id=\"timeFrameComboBox\" was not injected: check your FXML file 'MyData.fxml'.";
        timeFrameComboBox.setItems(FXCollections.observableArrayList(
                "1 Day", "3 Days", "5 Days", "7 Days", "30 Days", "60 Days", "90 Days", "365 Days"
        ));
        timeFrameComboBox.getSelectionModel().selectFirst(); // Optionally select a default item
    }

    /**
     * Constructor for MyData class.
     * Initializes the class with the current user and screen time entries DAO.
     * @param currentUser The current user.
     * @param screenTimeEntryDAO The DAO for screen time entries.
     */
    public MyData(User currentUser, IScreenTimeEntryDAO screenTimeEntryDAO) {
        this.currentUser = currentUser;
        this.screenTimeEntryDAO = screenTimeEntryDAO;
    }


    /**
     * Starts the MyData stage.
     * @param primaryStage The primary stage.
     * @throws Exception If an exception occurred.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/MyData.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        primaryStage.setTitle("MyData");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * Handles the delete data button click.
     * @throws SQLException If an SQL exception occurred.
     */
    @FXML
    private void handleConfirmDeletion() throws SQLException {
        String selected = timeFrameComboBox.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int days = Integer.parseInt(selected.split(" ")[0]); // Assuming format "X Days"
            confirmAndDelete(days);
        }
    }

    /**
     * Confirms the deletion of data based on the selected time frame.
     * @param days The number of days to delete data for.
     * @throws SQLException If an SQL exception occurred.
     */
    private void confirmAndDelete(int days) throws SQLException {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete data from the last " + days + " days?",
                ButtonType.YES, ButtonType.NO);
        confirmAlert.setTitle("Confirm Data Deletion");
        confirmAlert.setHeaderText("Data Deletion Confirmation");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            // Perform deletion
            deleteDataBasedOnSelection(days);
        }
    }

    /**
     * Deletes data based on the selected time frame.
     * @param days The number of days to delete data for.
     * @throws SQLException If an SQL exception occurred.
     */
    private void deleteDataBasedOnSelection(int days) throws SQLException {
        screenTimeEntryDAO.deleteUserDataWithinXDays(currentUser.getId(), days);
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Data Deleted");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText("All data older than " + days + " days has been successfully deleted.");
        infoAlert.showAndWait();
    }



    /**
     * Shows the privacy policy in an alert dialog.
     */
    @FXML
    private void showPrivacyPolicy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Privacy Policy");
        alert.setHeaderText(null);
        alert.setContentText("This is the privacy policy of the app. Your data is your own. It is stored locally on your machine and is your responsibility. We do not collect any data. We do not store any data. We do not share any data. We do not have access to your data. We do not have access to your machine. We do not have access to your personal information. We do not have access to your files. We do not have access to your screen time data. We do not have access to your applications. We do not have access to your browsing history. We do not have access to your personal information. We do not have access to your personal files. We do not have access to your personal data.");
        alert.showAndWait();
    }
    /**
     * Deletes all data after user confirmation and email validation.
     */
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
                if (isValidEmail(value) && Objects.equals(value, currentUser.getEmail())) {
                    try {
                        performDataDeletion();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
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
    /**
     * Validates user email
     */
    private boolean isValidEmail(String email) {
        // Basic email validation
        return email != null && email.contains("@") && email.contains(".");
    }
    /**
     * Informs of successful data delete.
     */
    private void performDataDeletion() throws SQLException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Data Deletion Confirmation");
        confirm.setHeaderText("Your data has been deleted");
        confirm.setContentText("Have a nice day.");
        // Logic to delete all data entries
        screenTimeEntryDAO.deleteUserData(currentUser.getId());
        System.out.println("All data entries deleted.");
    }
}

