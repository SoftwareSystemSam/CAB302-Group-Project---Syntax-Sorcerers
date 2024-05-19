package com.example.addressbook.GUI;


import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;


public class MyHubController extends Application {

    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;
    private Parent root; // Root layout of the scene
    private VBox contentArea; // Area to load content

    public MyHubController(User user, IScreenTimeEntryDAO screenDAO){
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }

    @Override
    public void start(Stage stage) throws SQLException {
        initUI(stage);
    }

    private void initUI(Stage stage) throws SQLException {

        Navigation navigationBar = new Navigation(currentUser, screenTimeEntryDAO);

        contentArea = new VBox();
        contentArea.setPrefSize(950, 600);

        // Create the root layout containing navigation bar and content area
        root = new BorderPane();
        ((BorderPane) root).setTop(navigationBar);
        ((BorderPane) root).setCenter(contentArea);

        var barChart = new BarChartGUI();
        var pieChart = new PieChartGUI(currentUser.getId(), screenTimeEntryDAO);

        // Add charts to the content area
        HBox chartsBox = new HBox(barChart, pieChart);
        contentArea.getChildren().add(chartsBox);


        // Create scene
        Scene scene = new Scene(root, 950, 600);

        // Set the scene and show the stage
        stage.setTitle("Combined Charts with Navigation Bar");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}