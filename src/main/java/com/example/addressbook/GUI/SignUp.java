package com.example.addressbook.GUI;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUp{
    @FXML
    private Button loginnButton;
    @FXML
    private TextArea idtextbox;
    @FXML
    private TextArea pstextbox;

    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        // Create a VBox to hold the navigation bar and graphs
        var vbox = new VBox();
        var scene = new Scene(vbox, 950, 500);

        stage.setTitle("Sign up for an account");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onLogIn() throws IOException {
        try {
            Stage stage = (Stage) loginnButton.getScene().getWindow();
            LogIn graphsWindow = new LogIn();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void idTexboxs() {
        idtextbox.setText("""
WELCOME TO THE SCREEN TRACKER APPLICATION. TICK THE BOX TO LOG IN AND MOVE ON.""");
    }
    @FXML
    public void psTexboxs() {
        pstextbox.setText("""
WELCOME TO THE SCREEN TRACKER APPLICATION. TICK THE BOX TO LOG IN AND MOVE ON.""");
    }
}