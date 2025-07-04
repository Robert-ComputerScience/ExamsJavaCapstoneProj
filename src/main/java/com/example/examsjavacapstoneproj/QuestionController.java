package com.example.examsjavacapstoneproj;

import com.example.examsjavacapstoneproj.model.Question;
import com.example.examsjavacapstoneproj.service.QuizDataService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class QuestionController {

    @FXML private Label questionLabel;
    @FXML private VBox optionsContainer;
    @FXML private Button previousButton;
    @FXML private Button nextButton;
    @FXML private ToggleGroup optionsGroup;

    private final QuizDataService quizService = new QuizDataService();
    private int questionIndex;

    @FXML
    public void initialize() {
        // Get the current question index from the shared state
        this.questionIndex = QuizState.getInstance().getCurrentQuestionIndex();
        displayQuestion();
    }

    private void displayQuestion() {
        Question question = quizService.getQuestion(this.questionIndex);
        if (question == null) return;

        questionLabel.setText(question.getQuestionText());

        // Create and display radio buttons for the options
        optionsContainer.getChildren().clear();
        for (String optionText : question.getOptions()) {
            RadioButton rb = new RadioButton(optionText);
            rb.setToggleGroup(optionsGroup);
            optionsContainer.getChildren().add(rb);
        }

        // Restore the user's previous answer for this question
        int savedAnswer = QuizState.getInstance().getAnswer(this.questionIndex);
        if (savedAnswer != -1 && savedAnswer < optionsContainer.getChildren().size()) {
            ((RadioButton) optionsContainer.getChildren().get(savedAnswer)).setSelected(true);
        }

        // Disable buttons if at the start or end of the quiz
        previousButton.setDisable(this.questionIndex == 0);
        nextButton.setDisable(this.questionIndex == quizService.getQuestionCount() - 1);
    }

    private void saveAnswer() {
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected != null) {
            int selectedIndex = optionsContainer.getChildren().indexOf(selected);
            QuizState.getInstance().setAnswer(this.questionIndex, selectedIndex);
        }
    }

    @FXML
    private void handleNext() {
        saveAnswer();
        if (questionIndex < quizService.getQuestionCount() - 1) {
            // Update the index in the shared state
            QuizState.getInstance().setCurrentQuestionIndex(questionIndex + 1);
            try {
                // Load the next question's FXML file
                PageLoader.loadPage("question_" + (questionIndex + 2) + ".fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handlePrevious() {
        saveAnswer();
        if (questionIndex > 0) {
            // Update the index in the shared state
            QuizState.getInstance().setCurrentQuestionIndex(questionIndex - 1);
            try {
                // Load the previous question's FXML file
                PageLoader.loadPage("question_" + (questionIndex) + ".fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}