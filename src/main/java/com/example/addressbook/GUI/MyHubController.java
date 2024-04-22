package com.example.addressbook.GUI;


import com.example.addressbook.SQL.Contact;
import com.example.addressbook.SQL.IContactDAO;
import com.example.addressbook.SQL.SqliteContactDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
        var root = new HBox();
        var scene = new Scene(root, 950, 330);

        // Instantiate BarChartPane and PieChartPane
        var barChart = new BarChartGUI();
        var pieChart = new PieChartGUI();

        // Adding both chart panes to the HBox
        root.getChildren().addAll(barChart, pieChart);

        stage.setTitle("Combined Charts");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}