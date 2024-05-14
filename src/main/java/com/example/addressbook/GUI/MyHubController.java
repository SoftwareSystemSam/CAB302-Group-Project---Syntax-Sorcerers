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


public class MyHubController extends Application {

    private ActiveWindowTracker windowTracker;
    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    public MyHubController(User user, IScreenTimeEntryDAO screenDAO, ActiveWindowTracker tracker){
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
        this.windowTracker = tracker;
    }
    @Override
    public void start(Stage stage) throws SQLException {
        initUI(stage);
    }

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