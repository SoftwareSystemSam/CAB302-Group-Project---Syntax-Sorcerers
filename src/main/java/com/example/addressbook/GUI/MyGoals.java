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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Sets up the navigation bar with buttons and their event handlers.
 */
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
    /**
     * Constructor for MyGoals class.
     * Initializes the class with the current user and screen time entries DAO.
     * @param user The current user.
     * @param screenDAO The DAO for screen time entries.
     */
    public MyGoals(User user, IScreenTimeEntryDAO screenDAO) {
        this.currentUser = user;
        this.screenTimeEntryDAO = screenDAO;
    }
    /**
     * Initializes the controller.
     * Sets up event handlers for the save and delete buttons.
     * Retrieves the goals from the database.
     */
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
    /**
     * Starts the MyGoals stage.
     * @param primaryStage The primary stage.
     * @throws Exception If an exception occurred.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/addressbook/MyGoals.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        primaryStage.setTitle("MyGoals");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    /**
     * Retrieves the goals from the database and adds them to the list view.
     * @throws SQLException If a database access error occurs.
     */
    private void getGoals() throws SQLException {
        List<String> goals = screenTimeEntryDAO.getGoalsByUserId(currentUser.getId());
        goalsListView.getItems().clear();
        goalsListView.getItems().addAll(goals);
    }
    /**
     * Handles the save button action.
     * Saves the entered goal to the database and adds it to the list view.
     * @param event The action event.
     */
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
    /**
     * Handles the delete button action.
     * Deletes the selected goal from the database and removes it from the list view.
     * @param event The action event.
     */
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
