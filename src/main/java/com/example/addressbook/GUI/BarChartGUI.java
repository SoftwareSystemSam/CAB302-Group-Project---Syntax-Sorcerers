package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
/**
 * This class is used to handle the bar chart GUI for MyHubController
 */
public class BarChartGUI extends HBox {

    private IScreenTimeEntryDAO screenTimeEntryDAO;
    private int userId;
    private BarChart<String, Number> barChart;
    /**
     * This function is used to create a new bar chart GUI
     * @param userId The user id
     * @param screenTimeEntryDAO The screen time entry DAO
     */
    public BarChartGUI(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) {
        this.userId = userId;
        this.screenTimeEntryDAO = screenTimeEntryDAO;
        initializeChart();
        setupAutoRefresh();
    }
    /**
     * This function is used to initialize the bar chart
     */
    private void initializeChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day of the Week");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Screen Time (Hours)");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Weekly Screen Time Distribution");

        populateChartData();
        getChildren().add(barChart);
    }
    /**
     * This function is used to populate the chart data
     */
    private void populateChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Screen Time");

        try {
            LocalDate today = LocalDate.now();
            LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
            Map<DayOfWeek, Long> weeklyData = screenTimeEntryDAO.getWeeklyScreenTimeByUserId(userId, startOfWeek);

            // Clear previous data
            barChart.getData().clear();

            // Populate the chart
            for (DayOfWeek day : DayOfWeek.values()) {
                Long durationInSeconds = weeklyData.getOrDefault(day, 0L);
                double durationInHours = durationInSeconds / 3600.0; // Convert seconds to hours
                series.getData().add(new XYChart.Data<>(day.name(), durationInHours));
            }
            barChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This function is used to set up the auto refresh
     */
    private void setupAutoRefresh() {
        Timeline refreshTimeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        ae -> populateChartData()
                ),
                new KeyFrame(
                        Duration.minutes(5)
                )
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
}
