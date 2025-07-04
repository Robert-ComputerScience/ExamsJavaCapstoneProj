package com.example.examsjavacapstoneproj.service;

import com.example.examsjavacapstoneproj.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class QuizDataService {
    private final List<Question> questions;

    public QuizDataService() {
        this.questions = loadQuestionsFromJson();
    }

    private List<Question> loadQuestionsFromJson() {
        // This uses the Gson dependency we added to the pom.xml
        try (InputStream stream = getClass().getResourceAsStream("/com/example/examsjavacapstoneproj/questions.json")) {
            if (stream == null) {
                System.err.println("Cannot find questions.json");
                return Collections.emptyList();
            }
            Gson gson = new Gson();
            Type questionListType = new TypeToken<List<Question>>(){}.getType();
            return gson.fromJson(new InputStreamReader(stream), questionListType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Question getQuestion(int index) {
        return (index >= 0 && index < questions.size()) ? questions.get(index) : null;
    }

    public int getQuestionCount() {
        return questions.size();
    }
}