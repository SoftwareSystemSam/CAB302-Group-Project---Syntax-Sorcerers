package com.example.addressbook.GUI;


import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MyHubController extends Application {

    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    public MyHubController(User user, IScreenTimeEntryDAO screenDAO){
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }
    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        // Create a navigation bar
        Navigation navigationBar = new Navigation();

        // Instantiate BarChartPane and PieChartPane
        var barChart = new BarChartGUI();
        var pieChart = new PieChartGUI(currentUser.getId(), screenTimeEntryDAO);

        // Create a VBox to hold the navigation bar and graphs
        var vbox = new VBox();
        var scene = new Scene(vbox, 950, 500);

        // Add the navigation bar and graphs to the VBox
        vbox.getChildren().addAll(navigationBar, new HBox(barChart, pieChart));

        stage.setTitle("Combined Charts with Navigation Bar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}