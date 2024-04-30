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


import java.io.IOException;
import java.util.List;

public class Navigation extends HBox {

    public Navigation() {
        // Create navigation buttons
        Button myGoalsB = new Button("My Goals");
        Button myNotificationsB = new Button("My Notifications");
        Button myStatsB = new Button("My Stats");
        Button myHealthB = new Button("My Health");
        Button myDataB = new Button("My Data");

        // Add buttons to the navigation bar
        this.getChildren().addAll(myGoalsB, myStatsB, myNotificationsB, myHealthB, myDataB);

        // Center the navigation bar horizontally
        this.setAlignment(Pos.CENTER);

        // Set event handlers for button clicks
        myGoalsB.setOnAction(event -> {
            // Handle My Goals button click
            // You can switch to My Goals view here
            System.out.println("My Goals button clicked");
            MyGoals myGoalsScene = new MyGoals();
            try {
                myGoalsScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myNotificationsB.setOnAction(event -> {
            // Handle My Notifications button click
            // You can switch to My Notifications view here
            System.out.println("My Notifications button clicked");
        });

        myStatsB.setOnAction(event -> {
            // Handle My Stats button click
            // You can switch to My Stats view here
            System.out.println("My Stats button clicked");
        });

        myHealthB.setOnAction(event -> {
            // Handle My Health button click
            // You can switch to My Health view here
            System.out.println("My Health button clicked");
            MyHealth myHealth = new MyHealth();
            try {
                myHealth.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myDataB.setOnAction(event -> {
            // Handle My Data button click
            // You can switch to My Data view here
            System.out.println("My Data button clicked");
        });
    }
}