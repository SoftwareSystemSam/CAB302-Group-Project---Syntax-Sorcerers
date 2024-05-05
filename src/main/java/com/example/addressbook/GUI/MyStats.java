package com.example.addressbook.GUI;


import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.IScreenTimeEntryDAO;
import com.example.addressbook.SQL.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
public class MyStats extends Application{

    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    @FXML
    private Button backButton;

    @FXML
    private Button manualEntryButton;

    @FXML
    protected void onBackButton() throws IOException {
    }

    @FXML
    protected void onManualEntryButton() throws IOException{

    }

    @FXML
    protected void onBack() throws IOException {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage stage) throws SQLException, IOException {
        initUI(stage);
    }

    private void initUI(Stage stage) throws SQLException, IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MyStats.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        stage.setTitle("MyStats");
        stage.setScene(scene);
        stage.show();
    }
}


