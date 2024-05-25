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
import javafx.scene.control.Label;
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
        contentArea.setPrefSize(1200, 700);

        var barChart = new BarChartGUI(currentUser.getId(), screenTimeEntryDAO);
        var pieChart = new PieChartGUI(currentUser.getId(), screenTimeEntryDAO);

        // Adjusting the layout for better control over the pie chart and bar chart
        HBox chartsBox = new HBox();
        chartsBox.setSpacing(20); // Adds space between the charts
        chartsBox.getChildren().add(barChart);
        VBox pieChartContainer = new VBox(pieChart); // Wrap pie chart for better control
        pieChartContainer.setPrefSize(450, 600); // Set preferred size for pie chart container
        chartsBox.getChildren().add(pieChartContainer);

        // Create toggle button with Ikonli icons
        ToggleButton toggleButton = new ToggleButton();
        FontIcon pauseIcon = new FontIcon("fa-pause");
        FontIcon playIcon = new FontIcon("fa-play");
        toggleButton.setGraphic(pauseIcon);  // Default to pause icon
        // Create label for the toggle button
        Label trackerLabel = new Label("Pause Screen Time Tracker");
        trackerLabel.setStyle("-fx-font-size: 16px; -fx-padding: 5px;");;

        toggleButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                toggleButton.setGraphic(playIcon); // Switch to play icon
                windowTracker.pause(); // Pause the tracker
                trackerLabel.setText("Resume Screen Time Tracker");
            } else {
                toggleButton.setGraphic(pauseIcon); // Switch back to pause icon
                windowTracker.resume(); // Resume the tracker
                trackerLabel.setText("Pause Screen Time Tracker");
            }
        });
        toggleButton.setMinSize(60, 60);

        var vbox = new VBox();
        vbox.setPrefSize(1200, 600); // Adjusted the size to fit new layout
        var scene = new Scene(vbox, 1200, 600); // Adjusted the scene size

        // Add charts to the content area
        contentArea.getChildren().add(chartsBox);



        vbox.getChildren().addAll(navigationBar, contentArea, toggleButton, trackerLabel);

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