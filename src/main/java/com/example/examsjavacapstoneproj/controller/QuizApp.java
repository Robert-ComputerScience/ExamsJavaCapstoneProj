package com.example.examsjavacapstoneproj.controller;

import com.example.examsjavacapstoneproj.QuizState;
import com.example.examsjavacapstoneproj.model.Question;
import com.example.examsjavacapstoneproj.service.QuizDataService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class QuizApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // 1. Get the shared QuizState instance
        QuizState quizState = QuizState.getInstance();
        quizState.setPrimaryStage(primaryStage);

        // 2. Load questions by calling the AI service
        QuizDataService dataService = new QuizDataService();
        // --- MODIFIED LINE ---
        // This now calls the AI service to generate a new test
        List<Question> loadedQuestions = dataService.generateAITest();

        // 3. Set the loaded questions into the shared state
        quizState.setQuestions(loadedQuestions);
        quizState.setCurrentQuestionIndex(0);

        // 4. Check if questions were loaded successfully
        if (loadedQuestions == null || loadedQuestions.isEmpty()) {
            System.err.println("FATAL ERROR: No questions were loaded from the AI service. Exiting.");
            // You could show an Alert here to inform the user of an internet/API issue
            return;
        }

        // 5. Now that the state is prepared, load the UI
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/examsjavacapstoneproj/QuestionView.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Java Tech Quiz");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}