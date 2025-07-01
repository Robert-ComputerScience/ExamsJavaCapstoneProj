package com.example.examsjavacapstoneproj;


import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

/**
 * A reusable controller for the standard question pages (1 through 14).
 * It handles loading question data, saving the user's answer, and navigating
 * to the next or previous question.
 */
public class QuestionController {

    @FXML
    private VBox rootPane; // Add fx:id="rootPane" to the root VBox in your FXML


    // This would be populated with logic to load question text and options
    // from a central source based on the current page number.
    // For this example, the logic is conceptual.

    /**
     * Saves the currently selected answer to the central QuizState.
     */
    private void saveCurrentAnswer() {
        // Conceptual: Get the question index (e.g., 0 for question 1).
        int questionIndex = 0; // This would be determined dynamically.

        // Conceptual: Get the selected radio button index (0-3).
        int selectedAnswerIndex = 1; // This would be read from the ToggleGroup.

        QuizState.getInstance().setAnswer(questionIndex, selectedAnswerIndex);
    }

    @FXML
    private void handleNext() throws IOException {
        // Get the name of the current FXML file (e.g., "question_2.fxml")
        String url = rootPane.getScene().getRoot().getProperties().get("URL").toString();
        String currentFxml = new File(url).getName();

        // Extract the number and calculate the next index
        int currentIndex = Integer.parseInt(currentFxml.replaceAll("[^0-9]", ""));
        int nextIndex = currentIndex + 1;

        // Construct just the simple filename for the next page
        String nextFxml = "question_" + nextIndex + ".fxml";

        // Pass the simple filename to the PageLoader
        PageLoader.loadPage(nextFxml);
    }

    // You would create a similar handlePrevious() method
    @FXML
    private void handlePrevious() throws IOException {
        String currentFxml = new File(rootPane.getScene().getRoot().getProperties().get("URL").toString()).getName();
        int currentIndex = Integer.parseInt(currentFxml.replaceAll("[^0-9]", ""));
        int prevIndex = currentIndex - 1;

        // Disable the "Previous" button if on the first question
        if (prevIndex > 0) {
            String prevFxml = "question_" + prevIndex + ".fxml";
            PageLoader.loadPage(prevFxml);
        }
    }
}

