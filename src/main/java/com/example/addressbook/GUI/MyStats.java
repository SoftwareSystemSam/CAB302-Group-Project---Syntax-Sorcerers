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
/**
 * MyStats class handles the user statistics settings.
 */
public class MyStats extends Application{

    /**
     * The title of the application window.
     */
    public static final String TITLE = "Screen Tracker";
    /**
     * The width of the application window.
     */
    public static final int WIDTH = 640;
    /**
     * The height of the application window.
     */
    public static final int HEIGHT = 360;

    /**
     * The back button.
     */
    @FXML
    private Button backButton;

    /**
     * The manual entry button.
     */
    @FXML
    private Button manualEntryButton;

    /**
     * The on back button.
     * @throws IOException If an exception occurs
     */
    @FXML
    protected void onBackButton() throws IOException {
    }

    /**
     * The on manual entry button.
     * @throws IOException If an exception occurs
     */
    @FXML
    protected void onManualEntryButton() throws IOException{

    }

    /**
     * This function is used to start the user statistics settings.
     * @throws IOException If an exception occurs
     */
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


