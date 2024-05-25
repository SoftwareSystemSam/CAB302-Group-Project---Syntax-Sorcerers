package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.ScreenTimeEntry;
import com.example.addressbook.SQL.User;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyStats2 extends Application {
    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;
    public MyStats2(User user, IScreenTimeEntryDAO screenDAO) {
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Label topAppsLabel;

    private List<String> mostUsedApps = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("MyStats2.fxml"));
            loader.setController(this);
            HBox root = loader.load();
            Scene scene = new Scene(root, 800, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("MyStats");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        barChart.setTitle("Most Used Applications");
        xAxis.setLabel("Application");
        yAxis.setLabel("Usage Count (Seconds)");
        loadChartData();
        loadTopApps();
    }

    private void loadChartData() {
        try {
            int userId = currentUser.getId();  // Assuming you have a currentUser object with an ID
            Map<String, ScreenTimeEntry> weeklyTopApps = screenTimeEntryDAO.getMostUsedAppForEachDayOfWeek(userId);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Top Apps of the Week");

            // Mapping day of week indices to names for better readability
            String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            // Clear the existing list to prevent duplication of data on refresh
            mostUsedApps.clear();

            // Ensure data for all days is present
            for (int i = 0; i < 7; i++) {
                String dayIndex = String.valueOf(i);
                ScreenTimeEntry entry = weeklyTopApps.get(dayIndex);
                if (entry != null) {
                    series.getData().add(new XYChart.Data<>(dayNames[i], entry.getDuration()));
                    mostUsedApps.add(dayNames[i] + " - " + entry.getApplicationName()); // Add the app name with the day
                } else {
                    // If no data is present for a day, add a zero value and null to the list
                    series.getData().add(new XYChart.Data<>(dayNames[i], 0));
                    mostUsedApps.add(dayNames[i] + " - None"); // Represent no data with "None"
                }
            }

            barChart.getData().clear();
            barChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load chart data: " + e.getMessage());
        }
    }

    private void loadTopApps() {
        StringBuilder labelContent = new StringBuilder("Most Used Applications:\n\n");
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
