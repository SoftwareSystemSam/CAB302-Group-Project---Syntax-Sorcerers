package com.example.addressbook.GUI;


import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;


public class MyHubController extends Application {

    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    public MyHubController(User user, IScreenTimeEntryDAO screenDAO){
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }

    @Override
    public void start(Stage stage) throws SQLException {
        initUI(stage);
    }

    private void initUI(Stage stage) throws SQLException {

        Navigation navigationBar = new Navigation();


        var barChart = new BarChartGUI();
        var pieChart = new PieChartGUI(currentUser.getId(), screenTimeEntryDAO);

        var vbox = new VBox();
        vbox.setPrefSize(950, 600);
        var scene = new Scene(vbox, 950, 600);


        vbox.getChildren().addAll(navigationBar, new HBox(barChart, pieChart));

        stage.setTitle("Combined Charts with Navigation Bar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}