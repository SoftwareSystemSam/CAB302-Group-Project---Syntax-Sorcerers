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
import java.sql.SQLException;
import java.util.Objects;

/**
 * This class is used to handle the MyHub controller
 */
public class MyHubController extends Application {

    private ActiveWindowTracker windowTracker;
    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

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

        Navigation navigationBar = new Navigation();


        var barChart = new BarChartGUI(currentUser.getId(), screenTimeEntryDAO);
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


        vbox.getChildren().addAll(navigationBar, new HBox(barChart, pieChart, toggleButton));

        stage.setTitle("Combined Charts with Navigation Bar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}