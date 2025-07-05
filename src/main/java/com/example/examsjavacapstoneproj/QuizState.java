package com.example.examsjavacapstoneproj;

import com.example.examsjavacapstoneproj.model.Question;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the global state of the quiz using the Singleton pattern.
 * This ensures that all controllers share the same instance for tracking answers
 * and the primary application window (Stage).
 */
public final class QuizState {

    private static final QuizState instance = new QuizState();

    private Stage primaryStage;
    private List<Question> questions;
    private int currentQuestionIndex = 0;

    // Use a Map for flexible answer storage (question index -> selected option index)
    private final Map<Integer, Integer> userAnswers = new HashMap<>();

    /**
     * Private constructor to prevent external instantiation.
     */
    private QuizState() {
        // Initialization is no longer needed for the Map
    }

    /**
     * The public method to get the single instance of this class.
     * @return The singleton QuizState instance.
     */
    public static QuizState getInstance() {
        return instance;
    }

    // --- Getters and Setters for State Management ---

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int index) {
        this.currentQuestionIndex = index;
    }

    public void setAnswer(int questionIndex, int answerIndex) {
        userAnswers.put(questionIndex, answerIndex);
    }

    public int getAnswer(int questionIndex) {
        // Returns the user's answer, or -1 if the question hasn't been answered
        return userAnswers.getOrDefault(questionIndex, -1);
    }

    public Map<Integer, Integer> getUserAnswers() {
        return this.userAnswers;
    }
}