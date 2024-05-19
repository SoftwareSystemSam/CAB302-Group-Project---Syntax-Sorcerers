package com.example.addressbook.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used to handle the health facts controller
 */
public class MyHealth extends Application {

    @FXML
    private Label healthFactsLabel;

    @FXML
    private Button loadFactsButton;

    private List<String> healthFacts = new ArrayList<>();

    /**
     * This function is used to start the health facts controller
     * @param primaryStage The stage to start
     * @throws Exception If an exception occurs
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/MyHealth.fxml"));
        loader.setController(this);
        Parent root = loader.load();

        // Set up primary stage
        primaryStage.setTitle("Health Facts");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();

        // Load initial facts
        loadHealthFacts();

        // Set action for the button
        loadFactsButton.setOnAction(event -> loadHealthFacts());
    }

    /**
     * This function is used to load health facts
     */
    private void loadHealthFacts() {
        // Add health facts about screen time
        healthFacts.clear();
        healthFacts.add("Excessive screen time may lead to eye strain.");
        healthFacts.add("Too much screen time can disrupt sleep patterns.");
        healthFacts.add("Reducing screen time can improve focus and attention span.");
        healthFacts.add("Limiting screen time can promote physical activity.");

        // Display a random health fact
        Random random = new Random();
        int index = random.nextInt(healthFacts.size());
        healthFactsLabel.setText(healthFacts.get(index));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
