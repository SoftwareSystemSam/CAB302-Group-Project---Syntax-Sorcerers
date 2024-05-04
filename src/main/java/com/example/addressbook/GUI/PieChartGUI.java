package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.ScreenTimeEntry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class PieChartGUI extends HBox {
    private int userId;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    public PieChartGUI(int userId, IScreenTimeEntryDAO screenTimeEntryDAO) {
        this.userId = userId;
        this.screenTimeEntryDAO = screenTimeEntryDAO;

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            List<ScreenTimeEntry> entries = screenTimeEntryDAO.getScreenTimeEntriesByUserId(userId);
            entries.forEach(entry -> {
                pieChartData.add(new PieChart.Data(entry.getApplicationName(), entry.getDuration()));
            });
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle errors properly
        }

        var pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Applications used throughout the day");

        getChildren().add(pieChart);
    }

}