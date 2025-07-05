module com.example.examsjavacapstoneproj {
    // Required modules for JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Often needed
    requires javafx.web;

    // Required modules for AI service and JSON parsing
    requires okhttp3;
    requires com.google.gson;

    // Open main package for JavaFX
    opens com.example.examsjavacapstoneproj to javafx.fxml, javafx.graphics;

    // Export and open the model package
    exports com.example.examsjavacapstoneproj.model;
    opens com.example.examsjavacapstoneproj.model to com.google.gson;

    // Export and open the controller package
    exports com.example.examsjavacapstoneproj.controller;
    opens com.example.examsjavacapstoneproj.controller to javafx.fxml;

    // Export the service package
    exports com.example.examsjavacapstoneproj.service;
    exports com.example.examsjavacapstoneproj;
}