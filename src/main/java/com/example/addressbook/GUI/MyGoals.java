package com.example.addressbook.GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class MyGoals extends Application {

    @FXML
    private TextArea goalInput;

    @FXML
    private ListView<String> goalsListView;

    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
        saveButton.setOnAction(this::saveGoal);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/addressbook/MyGoals.fxml")));
        primaryStage.setTitle("MyGoals");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private void saveGoal(ActionEvent event) {
        String goal = goalInput.getText().trim();
        if (!goal.isEmpty()) {
            goalsListView.getItems().add(goal);
            goalInput.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
