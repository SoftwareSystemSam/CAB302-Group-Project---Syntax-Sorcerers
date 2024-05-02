//package com.example.addressbook.GUI;
//
//import com.example.addressbook.HelloApplication;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class LogInController {
//
//    @FXML
//    private Button nextButton;
//    @FXML
//    private Button loginButton;
//    @FXML
//    private Button signupButton;
//
////    public void start(Stage stage) {initUI(stage);}
////    @FXML
////    public void initialize() {
////        termsAndConditions.setText("""
////WELCOME TO THE SCREEN TRACKER APPLICATION. TICK THE BOX TO LOG IN AND MOVE ON.""");
////    }
////
////    @FXML
////    protected void onAgreeCheckBoxClick() {
////        boolean accepted = agreeCheckBox.isSelected();
////        nextButton.setDisable(!accepted);
////    }
//
//    @FXML
//    protected void onNextButtonClick() throws IOException {
//        try {
//            Stage stage = (Stage) nextButton.getScene().getWindow();
//            LogIn graphsWindow = new LogIn();
//            graphsWindow.start(stage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @FXML
//    protected void onSignUpButtonClick() {
//        try {
//            Stage stage = (Stage) signupButton.getScene().getWindow();
//            SingIn graphsWindow = new SingIn();
//            graphsWindow.start(stage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void initUI(Stage stage) {
//        // Create a VBox to hold the navigation bar and graphs
//        var vbox = new VBox();
//        var scene = new Scene(vbox, 950, 500);
//
//        stage.setTitle("Log In and Register Bar");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @FXML
//    protected void onLogInButtonClick() throws IOException {
//        try {
//            Stage stage = (Stage) loginButton.getScene().getWindow();
//            LogIn graphsWindow = new LogIn();
//            graphsWindow.start(stage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    protected void onCancelButtonClick() {
//        Stage stage = (Stage) nextButton.getScene().getWindow();
//        stage.close();
//    }
//}