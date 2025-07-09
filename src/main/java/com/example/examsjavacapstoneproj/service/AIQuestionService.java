package com.example.examsjavacapstoneproj.service;

import com.example.examsjavacapstoneproj.model.Question;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class AIQuestionService {

    // ⚠️ IMPORTANT: Replace with your actual API key
    private static final String API_KEY = "YOUR_API_KEY_HERE"; // Replace with a valid key
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + API_KEY;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    /**
     * Generates questions for Java, HTML, and JavaFX.
     * @param count The number of questions to generate.
     * @return A list of questions.
     * @throws IOException If there is a network or API error.
     */
    public List<Question> generateJavaQuestions(int count) throws IOException {
        String prompt = String.format(
                "Generate %d unique, multiple-choice questions about Java, HTML, and JavaFX. " +
                        "Provide your response as a raw JSON array of objects. " +
                        "Each object must have exactly three keys: " +
                        "a 'questionText' (String), " +
                        "an 'options' (an array of 4 Strings), " +
                        "and a 'correctAnswerIndex' (integer from 0-3). " +
                        "Do not include any introductory text, explanations, or markdown formatting like ```json.",
                count
        );

        return executeApiCall(prompt);
    }

    /**
     * Generates questions for specified subjects and grade level.
     * @param subjects A string of subjects (e.g., "English, Math, History").
     * @param gradeLevel A string describing the grade level (e.g., "1 to 5").
     * @param numberOfQuestions The number of questions to generate.
     * @return A list of questions.
     * @throws IOException If there is a network or API error.
     */
    public List<Question> generateQuestions(String subjects, String gradeLevel, int numberOfQuestions) throws IOException {
        String prompt = String.format(
                "Generate %d unique, multiple-choice questions for the following subjects: %s. " +
                        "The questions should be appropriate for grade levels %s. " +
                        "Provide your response as a raw JSON array of objects. " +
                        "Each object must have exactly five keys: " +
                        "a 'questionText' (String), " +
                        "an 'options' (an array of 4 Strings), " +
                        "a 'correctAnswerIndex' (integer from 0-3), " +
                        "a 'subject' (String, one of: %s), " +
                        "and a 'gradeLevel' (Integer, from the specified range). " +
                        "Do not include any introductory text, explanations, or markdown formatting like ```json.",
                numberOfQuestions, subjects, gradeLevel, subjects
        );

        return executeApiCall(prompt);
    }

    /**
     * Executes the API call to the generative AI model with a given prompt.
     * @param prompt The prompt to send to the AI.
     * @return A list of questions parsed from the AI's response.
     * @throws IOException If there is a network or API error.
     */
    private List<Question> executeApiCall(String prompt) throws IOException {
        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\": \"" + prompt.replace("\"", "\\\"") + "\"}]}]}";
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected API response code " + response + " - " + response.body().string());
            }

            String responseBody = response.body().string();

            // Navigate the JSON response to get to the content text
            JsonObject responseObject = gson.fromJson(responseBody, JsonObject.class);
            String questionsJsonText = responseObject.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .get("content").getAsJsonObject()
                    .get("parts").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

            // Define the type for deserializing the list of questions
            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            return gson.fromJson(questionsJsonText, questionListType);
        } catch (Exception e) {
            System.err.println("Failed to parse AI response or execute request.");
            e.printStackTrace();
            return Collections.emptyList(); // Return empty list on error
        }
    }
}