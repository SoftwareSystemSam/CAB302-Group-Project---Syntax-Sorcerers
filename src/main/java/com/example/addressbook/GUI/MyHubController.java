package com.example.addressbook.GUI;


import com.example.addressbook.SQL.Contact;
import com.example.addressbook.SQL.IContactDAO;
import com.example.addressbook.SQL.SqliteContactDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.util.List;


public class MyHubController extends Application {
    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        // Create a navigation bar
        Navigation navigationBar = new Navigation();

        // Instantiate BarChartPane and PieChartPane
        var barChart = new BarChartGUI();
        var pieChart = new PieChartGUI();

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