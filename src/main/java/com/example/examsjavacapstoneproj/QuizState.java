package com.example.examsjavacapstoneproj;

import javafx.stage.Stage;

/**
 * Manages the global state of the quiz using the Singleton pattern.
 * This ensures that all controllers share the same instance for tracking answers
 * and the primary application window (Stage).
 */
public final class QuizState {

    // 1. Instance: A single, private, and final instance of the class.
    private static final QuizState instance = new QuizState();

    // 4. Stage Reference: Holds a reference to the main application window.
    private Stage primaryStage;

    // 2. User Answers: Stores the user's selection for each question.
    private final int[] userAnswers = new int[15];

    // 3. Correct Answers: A constant array holding the correct answer indexes.
    public final int[] correctAnswers = {2, 3, 1, 3, 2, 2, 1, 2, 2, 2, 2, 1, 2, 3, 2};

    /**
     * Private constructor to prevent external instantiation.
     * It initializes the userAnswers array to -1, signifying "unanswered".
     */
    private QuizState() {
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
    }

    /**
     * The public method to get the single instance of this class.
     * @return The singleton QuizState instance.
     */
    public static QuizState getInstance() {
        return instance;
    }

    // --- Public Methods for State Management ---

    /**
     * Stores a reference to the main application Stage.
     * @param stage The primary stage from the start() method.
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Retrieves the main application Stage.
     * @return The primary stage.
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    /**
     * Records the user's answer for a specific question.
     * @param questionIndex The index of the question (0-14).
     * @param answerIndex The index of the selected answer (0-3).
     */
    public void setAnswer(int questionIndex, int answerIndex) {
        if (questionIndex >= 0 && questionIndex < userAnswers.length) {
            this.userAnswers[questionIndex] = answerIndex;
        }
    }

    /**
     * Retrieves the user's recorded answer for a specific question.
     * @param questionIndex The index of the question (0-14).
     * @return The user's selected answer index, or -1 if unanswered.
     */
    public int getAnswer(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < userAnswers.length) {
            return this.userAnswers[questionIndex];
        }
        return -1; // Return -1 for an invalid index.
    }

    /**
     * Returns the entire array of user answers. Useful for final grade calculation.
     * @return The array of user answers.
     */
    public int[] getUserAnswers() {
        return this.userAnswers;
    }
}