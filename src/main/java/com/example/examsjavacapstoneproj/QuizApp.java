package com.example.examsjavacapstoneproj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The main entry point for the JavaFX Quiz Application.
 * This class is responsible for launching the application,
 * initializing the primary window (Stage), and loading the first question page.
 */
public class QuizApp extends Application {

    /**
     * This method is called when the application is launched.
     * It sets up the main window and displays the first question.
     *
     * @param primaryStage The primary window provided by the JavaFX runtime.
     * @throws IOException If the FXML file for the first question cannot be found or loaded.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Store the main stage in our singleton so controllers can access it for navigation.
        QuizState.getInstance().setPrimaryStage(primaryStage);

        // Load the user interface for the first question from its FXML file.
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/examsjavacapstoneproj/question_1.fxml"));

        // Create a new scene with the loaded UI.
        Scene scene = new Scene(root);

        // Apply the shared stylesheet to the scene.
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        // Set the window title and display the scene.
        primaryStage.setTitle("Java Tech Quiz | Question 1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}