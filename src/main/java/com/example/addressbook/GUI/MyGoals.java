package com.example.addressbook.GUI;

import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
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
    private Button deleteButton;

    private User currentUser;

    private IScreenTimeEntryDAO screenTimeEntryDAO;

    public MyGoals(User user, IScreenTimeEntryDAO screenDAO) {
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }

    @FXML
    private void initialize() {
        System.out.println("Initializing MyGoals controller");
        saveButton.setOnAction(this::saveGoal);
        deleteButton.setOnAction(this::deleteGoal);
        try{
            getGoals();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/MyGoals.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        primaryStage.setTitle("MyGoals");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private void getGoals() throws SQLException {
        List<String> goals = screenTimeEntryDAO.getGoalsByUserId(currentUser.getId());
        goalsListView.getItems().clear();
        goalsListView.getItems().addAll(goals);
    }

    private void saveGoal(ActionEvent event)  {
        System.out.println("Save goal button clicked");
        String goal = goalInput.getText();

        if (!goal.isEmpty()) {
            try{
                screenTimeEntryDAO.addGoal(currentUser.getId(), goal);
                goalsListView.getItems().add(goal);
                goalInput.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteGoal(ActionEvent event)  {
        System.out.println("Delete goal button clicked");
        String goal = goalsListView.getSelectionModel().getSelectedItem();

        if (goal != null) {
            try{
                screenTimeEntryDAO.deleteGoal(currentUser.getId(), goal);
                goalsListView.getItems().remove(goal);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
