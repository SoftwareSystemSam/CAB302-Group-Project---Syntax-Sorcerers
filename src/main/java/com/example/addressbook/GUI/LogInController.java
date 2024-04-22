package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    @FXML
    private TextArea termsAndConditions;
    @FXML
    private CheckBox agreeCheckBox;
    @FXML
    private Button nextButton;
    @FXML
    public void initialize() {
        termsAndConditions.setText("""
WELCOME TO THE SCREEN TRACKER APPLICATION. TICK THE BOX TO LOG IN AND MOVE ON.""");
    }

    @FXML
    protected void onAgreeCheckBoxClick() {
        boolean accepted = agreeCheckBox.isSelected();
        nextButton.setDisable(!accepted);
    }

    @FXML
    protected void onNextButtonClick() throws IOException {
        try {
            Stage stage = (Stage) nextButton.getScene().getWindow();
            MyHubController graphsWindow = new MyHubController();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }
}