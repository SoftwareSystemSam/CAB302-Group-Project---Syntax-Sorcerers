package com.example.addressbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main entry point for the application.
 */
public class HelloApplication extends Application {
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
         * Starts the application.
         * @param stage The primary stage for the application.
         * @throws IOException If an input or output exception occurred.
         */
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(com.example.addressbook.HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
            stage.setTitle(TITLE);
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Launches the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}