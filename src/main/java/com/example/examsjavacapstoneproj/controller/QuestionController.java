package com.example.examsjavacapstoneproj.controller;

import com.example.examsjavacapstoneproj.QuizState;
import com.example.examsjavacapstoneproj.model.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;

public class QuestionController {

    @FXML private Label questionNumberLabel; // Added missing field
    @FXML private Label questionLabel;
    @FXML private VBox optionsContainer;
    @FXML private ToggleGroup optionsGroup;
    @FXML private Button previousButton;
    @FXML private Button nextButton;
    // The fx:id for the submit button in your FXML is 'submitButton'
    @FXML private Button submitButton;

    private List<Question> questions;
    private int currentQuestionIndex;

    @FXML
    public void initialize() {
        // Get the entire quiz state
        QuizState quizState = QuizState.getInstance();
        this.questions = quizState.getQuestions();
        this.currentQuestionIndex = quizState.getCurrentQuestionIndex();

        displayQuestion();
    }

    private void displayQuestion() {
        if (questions == null || questions.isEmpty()) {
            questionLabel.setText("No questions loaded.");
            return;
        }

        // Get the current question object
        Question currentQuestion = questions.get(currentQuestionIndex);

        // This line updates the "Question X/15" label
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " / " + questions.size());

        // This updates the main question text
        questionLabel.setText(currentQuestion.getQuestionText());

        // This block updates the radio button options
        optionsContainer.getChildren().clear();
        for (String optionText : currentQuestion.getOptions()) {
            RadioButton rb = new RadioButton(optionText);
            rb.setToggleGroup(optionsGroup);
            optionsContainer.getChildren().add(rb);
        }

        // This restores the user's previously selected answer for this question
        int savedAnswer = QuizState.getInstance().getAnswer(currentQuestionIndex);
        if (savedAnswer != -1 && savedAnswer < optionsContainer.getChildren().size()) {
            ((RadioButton) optionsContainer.getChildren().get(savedAnswer)).setSelected(true);
        }

        // This updates the button states
        previousButton.setDisable(currentQuestionIndex == 0);

        // Change the "Next" button text to "Finish" on the last question
        if (currentQuestionIndex == questions.size() - 1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
    }

    private void saveAnswer() {
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected != null) {
            int selectedIndex = optionsContainer.getChildren().indexOf(selected);
            QuizState.getInstance().setAnswer(currentQuestionIndex, selectedIndex);
        }
    }

    @FXML
    private void handleNext() {
        saveAnswer(); // Save answer before moving
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            QuizState.getInstance().setCurrentQuestionIndex(currentQuestionIndex);
            displayQuestion();
        }
    }

    @FXML
    private void handlePrevious() {
        saveAnswer(); // Save answer before moving
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            QuizState.getInstance().setCurrentQuestionIndex(currentQuestionIndex);
            displayQuestion();
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        saveAnswer();

        QuizState quizState = QuizState.getInstance();
        List<Question> questions = quizState.getQuestions();
        Map<Integer, Integer> userAnswers = quizState.getUserAnswers();

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int correctAnswerIndex = question.getCorrectAnswerIndex();
            int userAnswerIndex = userAnswers.getOrDefault(i, -1);

            if (userAnswerIndex == correctAnswerIndex) {
                score++;
            }
        }

        double percentage = (double) score / questions.size() * 100;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Results");
        alert.setHeaderText("You have completed the quiz!");
        alert.setContentText(String.format("Your score: %d out of %d (%.2f%%)", score, questions.size(), percentage));
        alert.showAndWait();
    }
}