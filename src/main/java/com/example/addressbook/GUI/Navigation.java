package com.example.addressbook.GUI;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;


/**
 * The Navigation class extends HBox and represents the navigation bar of the application.
 * It contains three buttons: "My Goals", "My Stats", and "My Health".
 * Each button is associated with an event handler that handles the button click event.
 */
public class Navigation extends HBox {

    /**
     * The constructor of the Navigation class.
     * It initializes the navigation bar with three buttons and sets their event handlers.
     */
    public Navigation() {
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
            System.out.println("My Goals button clicked");
            MyGoals myGoalsScene = new MyGoals();
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
            MyStats2 myStatsScene = new MyStats2();
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
            System.out.println("My Goals button clicked");
            MyHealth myMyHealthScene = new MyHealth();
            try {
                myMyHealthScene.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}