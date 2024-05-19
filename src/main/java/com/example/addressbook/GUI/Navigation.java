package com.example.addressbook.GUI;
import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
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
import javafx.scene.Node;
import javafx.scene.Parent;


import java.io.IOException;
import java.util.List;

public class Navigation extends HBox {
    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    public Navigation(User user, IScreenTimeEntryDAO screenDAO) {
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
        setupNavigationBar();

    }

    public Navigation(){
        setupNavigationBar();
    }

    private void setupNavigationBar() {
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
            System.out.println("My Notifications button clicked");
        });

        myStatsB.setOnAction(event -> {
            // Handle My Stats button click
            System.out.println("My Stats button clicked");
            MyStats2 myStatsScene = new MyStats2(currentUser,screenTimeEntryDAO);
            try {
                myStatsScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myHealthB.setOnAction(event -> {
            // Handle My Health button click
            System.out.println("My Health button clicked");
            MyHealth myMyHealthScene = new MyHealth();
            try {
                myMyHealthScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myDataB.setOnAction(event -> {
            // Handle My Data button click
            System.out.println("My Data button clicked");
        });
    }
}