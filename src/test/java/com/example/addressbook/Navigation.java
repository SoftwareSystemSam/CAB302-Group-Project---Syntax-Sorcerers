package com.example.addressbook;

import com.example.addressbook.GUI.Navigation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;


class NavigationTest extends ApplicationTest {

    private FxRobot robot;
    private Navigation navigation;

    @Override
    public void start(Stage stage) {
        navigation = new Navigation();
        Scene scene = new Scene(navigation, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setup() {
        robot = new FxRobot();
    }

    @Test
    void testMyGoalsButton() {
        // Click on the "My Goals" button
        robot.clickOn("My Goals");

        // Verify the expected action
        assertTrue(robot.lookup(".button").queryButton().isVisible());
    }

    @Test
    void testMyNotificationsButton() {
        // Click on the "My Notifications" button
        robot.clickOn("My Notifications");

        // Verify the expected action
        assertTrue(robot.lookup(".button").queryButton().isVisible());
    }

    @Test
    void testMyStatsButton() {
        // Click on the "My Stats" button
        robot.clickOn("My Stats");

        // Verify the expected action
        assertTrue(robot.lookup(".button").queryButton().isVisible());
    }

    @Test
    void testMyHealthButton() {
        // Click on the "My Health" button
        robot.clickOn("My Health");

        // Verify the expected action
        assertTrue(robot.lookup(".button").queryButton().isVisible());
    }

    @Test
    void testMyDataButton() {
        // Click on the "My Data" button
        robot.clickOn("My Data");

        // Verify the expected action
        assertTrue(robot.lookup(".button").queryButton().isVisible());
    }
}
