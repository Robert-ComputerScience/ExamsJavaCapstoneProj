package com.example.examsjavacapstoneproj;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * A utility class with a static method to handle view/page navigation.
 * This centralizes the logic for swapping FXML content within the main application window,
 * making the controllers cleaner and avoiding code duplication.
 */
public class PageLoader {

    public static void loadPage(String simpleFxmlFile) throws IOException {
        // This is the full path inside the resources folder
        final String FXML_PATH = "/com/example/examsjavacapstoneproj/";

        Stage stage = QuizState.getInstance().getPrimaryStage();

        // Combine the path and the simple filename to create a full, absolute path
        Parent newRoot = FXMLLoader.load(PageLoader.class.getResource(FXML_PATH + simpleFxmlFile));

        Scene scene = stage.getScene();
        scene.setRoot(newRoot);
    }
}