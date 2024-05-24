package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;

/**
 * This class is used to handle the health facts controller
 */
public class MyHealth extends Application {
    @FXML
    private Label healthFactsLabel;
    @FXML
    private Button loadFactsButton;
    private List<String> healthFacts = new ArrayList<>();
    @FXML
    private Button backButton;
    private  Parent root;

    /**
     * This function is used to start the health facts controller
     * @param primaryStage The stage to start
     * @throws Exception If an exception occurs
     */
    @Override
    public void start(Stage primaryStage) {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/MyHealth.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
         }catch (Exception e) {
            e.printStackTrace();
        }

        // Set up primary stage
        primaryStage.setTitle("Health Facts");
        primaryStage.setScene(new Scene(root, 500, 300));
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
        healthFacts.add("Excessive screen time can lead to digital eye strain, causing symptoms like dry eyes, blurred vision, and headaches.");
        healthFacts.add("Prolonged screen time, especially before bedtime, can disrupt sleep patterns, leading to insomnia and poor quality sleep.");
        healthFacts.add("Reducing screen time can improve focus and attention span.");
        healthFacts.add("Limiting screen time can promote physical activity.");
        healthFacts.add("Excessive screen time, particularly on social media, can contribute to feelings of loneliness, depression, and anxiety, especially in adolescents.");
        healthFacts.add("Spending too much time on screens may negatively impact posture, leading to musculoskeletal issues like neck and back pain.");
        healthFacts.add("Long hours of screen use can contribute to digital addiction, where individuals have difficulty controlling their screen habits despite negative consequences.");
        healthFacts.add("Excessive screen time can impair social skills development, as face-to-face interaction and non-verbal communication are limited.");
        healthFacts.add("Heavy screen use may contribute to the development of digital eye diseases like myopia (nearsightedness) in children and adolescents.");
        healthFacts.add("Screen time can disrupt family dynamics and interpersonal relationships when it becomes a primary source of entertainment or communication within households.");

        // Display a random health fact
        Random random = new Random();
        int index = random.nextInt(healthFacts.size());
        healthFactsLabel.setText(healthFacts.get(index));
    }

}
