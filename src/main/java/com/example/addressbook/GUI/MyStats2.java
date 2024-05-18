package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MyStats2 extends Application {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Label topAppsLabel;

    private List<String> mostUsedApps = List.of("App A", "App B", "App C", "App D", "App E");
    private Map<String, Integer> appUsageData = Map.of(
            "App A", 100,
            "App B", 80,
            "App C", 60,
            "App D", 40,
            "App E", 20
    );

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("MyStats2.fxml"));
            HBox root = loader.load();
            Scene scene = new Scene(root, 800, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("App Usage Analyzer");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        barChart.setTitle("Most Used Applications");
        xAxis.setLabel("Application");
        yAxis.setLabel("Usage Count");
        loadChartData();
        loadTopApps();
    }

    private void loadChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String appName : mostUsedApps) {
            series.getData().add(new XYChart.Data<>(appName, appUsageData.getOrDefault(appName, 0)));
        }
        barChart.getData().add(series);
    }

    private void loadTopApps() {
        StringBuilder labelContent = new StringBuilder("Top Most Used Applications:\n");
        for (String appName : mostUsedApps) {
            labelContent.append(appName).append("\n");
        }
        topAppsLabel.setText(labelContent.toString());
    }

    @FXML
    private void handleRefresh() {
        barChart.getData().clear();
        loadChartData();
        loadTopApps();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) barChart.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
