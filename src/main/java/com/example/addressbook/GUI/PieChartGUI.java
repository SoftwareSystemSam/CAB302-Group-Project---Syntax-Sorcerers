package com.example.addressbook.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PieChartGUI extends HBox {
    public PieChartGUI() {

        var root = new HBox();

        var scene = new Scene(root, 450, 450);

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                new PieChart.Data("Youtube", 52),
                new PieChart.Data("Google", 31),
                new PieChart.Data("Fortnite", 12),
                new PieChart.Data("University", 2),
                new PieChart.Data("Coding", 1),
                new PieChart.Data("Others", 2));

        var pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Applications used throughout the day");

        getChildren().add(pieChart);
    }

}