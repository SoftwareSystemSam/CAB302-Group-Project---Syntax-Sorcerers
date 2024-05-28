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
 * This class is used to handle the navigation bar
 */
public class Navigation extends HBox {
    private User currentUser;
    private IScreenTimeEntryDAO screenTimeEntryDAO;

    /**
     * This function is used to create a new navigation bar
     */
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
        Button myStatsB = new Button("My Stats");
        Button myHealthB = new Button("My Health");

        // Add buttons to the navigation bar
        this.getChildren().addAll(myGoalsB, myStatsB, myHealthB);

        // Center the navigation bar horizontally
        this.setAlignment(Pos.CENTER);

        // Set event handlers for button clicks

        /*
         * Set the event handler for the "My Goals" button.
         * When the button is clicked, it prints a message to the console and opens the MyGoals scene.
         */
        myGoalsB.setOnAction(event -> {
            // Handle My Goals button click
            System.out.println("My Goals button clicked");
            MyGoals myGoalsScene = new MyGoals(currentUser,screenTimeEntryDAO);
            try {
                myGoalsScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        /*
         * Set the event handler for the "My Stats" button.
         * When the button is clicked, it prints a message to the console and opens the MyStats2 scene.
         */
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
            System.out.println("My Health button clicked");
            MyHealth myMyHealthScene = new MyHealth();
            try {
                myMyHealthScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}