package com.example.addressbook.GUI;

import com.example.addressbook.HelloApplication;
import com.example.addressbook.SQL.IUserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import com.example.addressbook.SQL.UserService;
import net.bytebuddy.agent.VirtualMachine;

public class SignUp {
    @FXML
    private Button signupButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    private UserService userService = new UserService();

    public static final String TITLE = "Screen Tracker";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;


    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signUp-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void signupButtonAction() throws IOException {
        //get text
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Output the text written in the text box to the console.
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        boolean success = userService.registerNewUser(email, password);

        // Handle navigation back to the login page
        Stage stage = (Stage) signupButton.getScene().getWindow();
        MyHubController loginWindow = new MyHubController();
        loginWindow.start(stage);
    }

    @FXML
    protected void onBack() throws IOException {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            LogInController graphsWindow = new LogInController();
            graphsWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}