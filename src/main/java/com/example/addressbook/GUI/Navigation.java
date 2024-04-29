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
        Button MyGoals = new Button("My Goals");

//        @FXML
//        protected void MyGoalsButtonClick() throws IOException {
//            try {
//                Stage stage = (Stage) nextButton.getScene().getWindow();
//                MyGoals MyGoalsWindow = new MyGoals();
//                MyGoalsWindow.start(stage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        Button MyStats = new Button("My Stats");

//        @FXML
//        protected void MyStatsButtonClick() throws IOException {
//            try {
//                Stage stage = (Stage) nextButton.getScene().getWindow();
//                MyHubController graphsWindow = new MyHubController();
//                graphsWindow.start(stage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        Button MyNotifications = new Button("My Notifications");

//        @FXML
//        protected void MyNotificationsButtonClick() throws IOException {
//            try {
//                Stage stage = (Stage) nextButton.getScene().getWindow();
//                MyHubController graphsWindow = new MyHubController();
//                graphsWindow.start(stage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        Button MyHealth = new Button("My Health");

//        @FXML
//        protected void MyHealthButtonClick() throws IOException {
//            try {
//                Stage stage = (Stage) nextButton.getScene().getWindow();
//                MyHubController graphsWindow = new MyHubController();
//                graphsWindow.start(stage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        Button MyData = new Button("My Data");

//        @FXML
//        protected void MyDataButtonClick() throws IOException {
//            try {
//                Stage stage = (Stage) nextButton.getScene().getWindow();
//                MyHubController graphsWindow = new MyHubController();
//                graphsWindow.start(stage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        // Add buttons to the navigation bar
        this.getChildren().addAll(MyGoals, MyStats, MyNotifications, MyHealth, MyData);

        // Center the navigation bar horizontally
        this.setAlignment(Pos.CENTER);
    }
}