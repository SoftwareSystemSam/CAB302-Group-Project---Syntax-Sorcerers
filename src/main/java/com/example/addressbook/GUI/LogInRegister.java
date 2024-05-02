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
//public class LogInRegister{
//    @FXML
//    private Button loginButton;
//    @FXML
//    private Button signupButton;
//
//    public void start(Stage stage) {initUI(stage);}
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
//    protected void onSignUpButtonClick() {
//        try {
//            Stage stage = (Stage) signupButton.getScene().getWindow();
//            SignIn graphsWindow = new SignIn();
//            graphsWindow.start(stage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}