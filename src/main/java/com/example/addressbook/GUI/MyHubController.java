package com.example.addressbook.GUI;


import com.example.addressbook.SQL.ActiveWindowTracker;
import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Handles the password field action.
 * Throws IOException If an input or output exception occurred.
 */
public class MyHubController extends Application {

    private ActiveWindowTracker windowTracker;
    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    private VBox contentArea; // Area to load content

    private Parent root; // Root layout of the scene

    /**
     * This function is used to create a new MyHub controller
     * @param user The user
     * @param screenDAO The screen time entry DAO
     * @param tracker The active window tracker
     */
    public MyHubController(User user, IScreenTimeEntryDAO screenDAO, ActiveWindowTracker tracker){
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
        this.windowTracker = tracker;
    }
    /**
     * This function is used to start the MyHub controller
     * @param stage The stage to start
     * @throws SQLException If an exception occurs
     */
    @Override
    public void start(Stage stage) throws SQLException {
        initUI(stage);
    }

    /**
     * This function is used to initialize the user interface
     * @param stage The stage to initialize
     * @throws SQLException If an exception occurs
     */
    private void initUI(Stage stage) throws SQLException {

        Navigation navigationBar = new Navigation(currentUser, screenTimeEntryDAO);

        contentArea = new VBox();
        contentArea.setPrefSize(950, 600);

        var barChart = new BarChartGUI(currentUser.getId(), screenTimeEntryDAO);
        // Create the root layout containing navigation bar and content area
        root = new BorderPane();
        ((BorderPane) root).setTop(navigationBar);
        ((BorderPane) root).setCenter(contentArea);
        var pieChart = new PieChartGUI(currentUser.getId(), screenTimeEntryDAO);

        // Create toggle button with Ikonli icons
        ToggleButton toggleButton = new ToggleButton();
        FontIcon pauseIcon = new FontIcon("fa-pause");
        FontIcon playIcon = new FontIcon("fa-play");
        toggleButton.setGraphic(pauseIcon);  // Default to pause icon

        toggleButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                toggleButton.setGraphic(playIcon); // Switch to play icon
                windowTracker.pause(); // Pause the tracker
            } else {
                toggleButton.setGraphic(pauseIcon); // Switch back to pause icon
                windowTracker.resume(); // Resume the tracker
            }
        });
        toggleButton.setMinSize(60, 60);


        var vbox = new VBox();
        vbox.setPrefSize(950, 600);
        var scene = new Scene(vbox, 950, 600);
        // Add charts to the content area
        HBox chartsBox = new HBox(barChart, pieChart);
        contentArea.getChildren().add(chartsBox);


        vbox.getChildren().addAll(navigationBar, new HBox(barChart, pieChart, toggleButton));

        // Set the scene and show the stage
        stage.setTitle("Combined Charts with Navigation Bar");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The main entry point for all JavaFX applications.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}