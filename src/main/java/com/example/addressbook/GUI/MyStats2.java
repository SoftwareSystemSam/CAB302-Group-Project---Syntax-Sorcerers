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
/**
 * MyStats2 class handles the user statistics settings.
 */
public class MyStats2 extends Application {
    /**
     * The current user.
     */
    private User currentUser;
    /**
     * The screen time entry DAO.
     */
    private IScreenTimeEntryDAO screenTimeEntryDAO;
    /**
     * Constructs a new MyStats2 object.
     * @param user The current user
     * @param screenDAO The screen time entry DAO
     */
    public MyStats2(User user, IScreenTimeEntryDAO screenDAO) {
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }

    /**
     * The bar chart.
     */
    @FXML
    private BarChart<String, Number> barChart;
    /**
     * The x-axis.
     */
    @FXML
    private CategoryAxis xAxis;
    /**
     * The y-axis.
     */
    @FXML
    private NumberAxis yAxis;
    /**
     * The top apps label.
     */
    @FXML
    private Label topAppsLabel;
    /**
     * The most used apps list.
     */

    private List<String> mostUsedApps = new ArrayList<>();

    /**
     * Starts the MyStats2 application.
     * @param primaryStage The primary stage
     */
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

    /**
     * Initializes the MyStats2 class.
     */
    @FXML
    public void initialize() {
        barChart.setTitle("Most Used Applications");
        xAxis.setLabel("Application");
        yAxis.setLabel("Usage Count (Seconds)");
        loadChartData();
        loadTopApps();
    }
    /**
     * Loads the chart data.
     * @throws SQLException If an exception occurs
     */
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

    /**
     * Loads the top apps.
     */
    private void loadTopApps() {
        StringBuilder labelContent = new StringBuilder("Most Used Applications:\n\n");
        for (String appName : mostUsedApps) {
            labelContent.append(appName).append("\n");
        }
        topAppsLabel.setText(labelContent.toString());
    }

    /**
     * Handles the refresh action.
     */
    @FXML
    private void handleRefresh() {
        barChart.getData().clear();
        loadChartData();
        loadTopApps();
    }

    /**
     * Handles the close action.
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) barChart.getScene().getWindow();
        stage.close();
    }

    /**
     * The main method.
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
