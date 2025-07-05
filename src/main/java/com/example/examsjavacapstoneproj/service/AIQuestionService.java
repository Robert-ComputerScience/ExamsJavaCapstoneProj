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
    private static final String API_KEY = "AIzaSyD3Iuopbn9aWchlN1tnV3YOZa_c_if4C58";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + API_KEY;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public List<Question> generateJavaQuestions(int count) throws IOException {
        // --- CORRECTED PROMPT ---
        // This now asks for 'correctAnswerIndex' as an integer (0, 1, 2, or 3).
        String prompt = String.format(
                "Generate %d unique, multiple-choice questions about Java, HTML, and JavaFX. " +
                        "Provide your response as a raw JSON array of objects. " +
                        "Each object must have exactly three keys: " +
                        "a 'questionText' (String), " +
                        "an 'options' (an array of 4 Strings), " +
                        "and a 'correctAnswerIndex' (integer). " +
                        "The 'correctAnswerIndex' must be the zero-based index of the correct answer in the 'options' array. " +
                        "Do not include any introductory text, explanations, or markdown formatting like ```json.",
                count
        );

        String jsonBody = "{\"contents\":[{\"parts\":[{\"text\": \"" + prompt + "\"}]}]}";
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response + " - " + response.body().string());
            }

            String responseBody = response.body().string();

            JsonObject responseObject = gson.fromJson(responseBody, JsonObject.class);
            String questionsJsonText = responseObject.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .get("content").getAsJsonObject()
                    .get("parts").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            return gson.fromJson(questionsJsonText, questionListType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}