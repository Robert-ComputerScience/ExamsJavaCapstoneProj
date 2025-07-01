package com.example.examsjavacapstoneproj;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * A specific controller for the final question page (question_15.fxml).
 * It handles the final answer submission and calculates the total score.
 */
public class FinalQuestionController {

    @FXML
    private VBox optionsContainer; // Container for radio buttons to disable after submit.

    @FXML
    private Label gradeLabel; // Label to display the final score.

    /**
     * Handles the 'Submit' button click.
     * It calculates the final score and displays it to the user.
     */
    @FXML
    private void handleSubmit() {
        // Conceptual: Save the answer for the final question first.
        saveFinalAnswer();

        // Array of correct answer indexes (0=A, 1=B, etc.)
        int[] correctAnswers = {2, 3, 1, 3, 2, 2, 1, 2, 2, 2, 2, 1, 2, 3, 2}; // Example answers

        // Get the user's answers from the state manager.
        int[] userAnswers = QuizState.getInstance().getUserAnswers();

        // Calculate the score.
        int score = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }

        double percentage = ((double) score / correctAnswers.length) * 100;

        // Display the final grade.
        gradeLabel.setText(String.format("Your Grade: %d/%d (%.2f%%)", score, correctAnswers.length, percentage));

        // Disable the options to prevent changes after submission.
        optionsContainer.setDisable(true);
    }

    /**
     * Saves the selected answer for the final question to the QuizState.
     */
    private void saveFinalAnswer() {
        // Conceptual: Get the selected radio button index for question 15.
        int finalAnswerIndex = 2; // This would be read from the ToggleGroup.
        QuizState.getInstance().setAnswer(14, finalAnswerIndex); // 14 is the index for question 15.
    }

    @FXML
    private void handlePrevious() {
        try {
            // Since this is question 15, "previous" is always question 14.
            PageLoader.loadPage("question_14.fxml");
        } catch (IOException e) {
            System.err.println("Failed to load previous page.");
            e.printStackTrace();
        }
    }
}