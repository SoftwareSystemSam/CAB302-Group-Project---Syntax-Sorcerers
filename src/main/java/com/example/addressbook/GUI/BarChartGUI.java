package com.example.addressbook.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class BarChartGUI extends HBox {

    public BarChartGUI() {

        var root = new HBox();

        var scene = new Scene(root, 450, 450);
        var xAxis = new CategoryAxis();

        var yAxis = new NumberAxis();
        yAxis.setLabel("Hours doing Uni");

        var barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Days of the Week Type Beat");

        var data = new XYChart.Series<String, Number>();

        data.getData().add(new XYChart.Data<>("Sunday", 46));
        data.getData().add(new XYChart.Data<>("Monday", 38));
        data.getData().add(new XYChart.Data<>("Tuesday", 29));
        data.getData().add(new XYChart.Data<>("Wednesday", 22));
        data.getData().add(new XYChart.Data<>("Thursday", 13));
        data.getData().add(new XYChart.Data<>("Friday", 11));
        data.getData().add(new XYChart.Data<>("Saturday", 19));

        barChart.getData().add(data);
        barChart.setLegendVisible(false);

        getChildren().add(barChart);

    }

}