package com.example.addressbook.GUI;
import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

/**
 * Navigation class represents the navigation bar of the application.
 */
public class Navigation extends HBox {
    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    /**
     * Constructor for Navigation class.
     * Initializes the navigation bar with the current user and screen time entries DAO.
     * @param user The current user.
     * @param screenDAO The DAO for screen time entries.
     */
    public Navigation(User user, IScreenTimeEntryDAO screenDAO) {
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
        setupNavigationBar();

    }
    /**
     * Default constructor for Navigation class.
     * Initializes the navigation bar without user and screen time entries DAO.
     */
    public Navigation(){
        setupNavigationBar();
    }
    /**
     * Sets up the navigation bar with buttons and their event handlers.
     */
    private void setupNavigationBar() {
        // Create navigation buttons
        Button myGoalsB = new Button("My Goals");
        Button myStatsB = new Button("My Stats");
        Button myHealthB = new Button("My Health");
        Button myDataB = new Button("My Data");
        Button myNotificationsB = new Button("My Notifications");

        // Add buttons to the navigation bar
        this.getChildren().addAll(myGoalsB, myStatsB, myHealthB, myNotificationsB, myDataB);

        // Center the navigation bar horizontally
        this.setAlignment(Pos.CENTER);

        // Set event handlers for button clicks

        /*
         * Set the event handler for the "My Goals" button.
         * When the button is clicked, it prints a message to the console and opens the MyGoals scene.
         */
        myGoalsB.setOnAction(event -> {
            // Handle My Goals button click
            MyGoals myGoalsScene = new MyGoals(currentUser,screenTimeEntryDAO);
            try {
                myGoalsScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myNotificationsB.setOnAction(event -> {
            // Handle My Notifications button click
            // You can switch to My Notifications view here
            MyNotifications myNotificationsScene = new MyNotifications(currentUser,screenTimeEntryDAO);
            try {
                myNotificationsScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myStatsB.setOnAction(event -> {
            System.out.println("My Stats button clicked");
            MyStats2 myStatsScene = new MyStats2(currentUser,screenTimeEntryDAO);
            try {
                myStatsScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        /*
         * Set the event handler for the "My Health" button.
         * When the button is clicked, it prints a message to the console and opens the MyHealth scene.
         */
        myHealthB.setOnAction(event -> {
            // Handle My Health button click
            // You can switch to My Health view here
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
            // You can switch to My Data view here
            MyData MyDataScene = new MyData(currentUser,screenTimeEntryDAO);
            try {
                MyDataScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}