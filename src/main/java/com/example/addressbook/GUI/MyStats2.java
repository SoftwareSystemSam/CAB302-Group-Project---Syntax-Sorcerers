package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Map;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MyStats2 extends Application {
    private Parent root;

    @Override
    public void start(Stage primaryStage) {
        // Sample data - replace this with your actual data
        List<String> mostUsedApps = List.of("App A", "App B", "App C", "App D", "App E");
        Map<String, Integer> appUsageData = Map.of(
                "App A", 100,
                "App B", 80,
                "App C", 60,
                "App D", 40,
                "App E", 20
        );

        // Create a bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Most Used Applications");
        xAxis.setLabel("Application");
        yAxis.setLabel("Usage Count");

        // Prepare data for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String appName : mostUsedApps) {
            series.getData().add(new XYChart.Data<>(appName, appUsageData.getOrDefault(appName, 0)));
        }

        // Add data to the chart
        barChart.getData().add(series);

        // Create a label to display top most used apps
        Label topAppsLabel = new Label("Top Most Used Applications:\n");
        for (String appName : mostUsedApps) {
            topAppsLabel.setText(topAppsLabel.getText() + appName + "\n");
        }

        // Create layout
        HBox root = new HBox(10);
        root.getChildren().addAll(barChart, topAppsLabel);

        // Center the content vertically and horizontally
        root.setAlignment(javafx.geometry.Pos.CENTER);

        // Set up scene
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("App Usage Analyzer");
        primaryStage.show();
    }
}




