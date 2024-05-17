import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import com.example.addressbook.HelloApplication;
import com.example.addressbook.GUI.LogInController;

@ExtendWith(ApplicationExtension.class)
class LogInControllerTest {

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testLoginButtonClick(FxRobot robot) {
        // Click on the login button
        robot.clickOn("#loginButton");

        // Add a short delay to allow the new stage to appear
        robot.sleep(500);

        // Verify that the new stage (or scene) has been loaded
        Stage newStage = (Stage) robot.targetWindow();
        assertEquals("Screen Tracker", newStage.getTitle());

        // Optionally, verify other aspects of the new stage/scene
    }

    @Test
    void testSignupButtonClick(FxRobot robot) {
        // Click on the signup button
        robot.clickOn("#signupButton");

        // Add a short delay to allow the new stage to appear
        robot.sleep(500);

        // Verify that the new stage (or scene) has been loaded
        Stage newStage = (Stage) robot.targetWindow();
        assertEquals("Screen Tracker", newStage.getTitle());

        // Optionally, verify other aspects of the new stage/scene
    }
}