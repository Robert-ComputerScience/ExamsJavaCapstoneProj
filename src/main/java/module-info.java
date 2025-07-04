module com.example.examsjavacapstoneproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;

    opens com.example.examsjavacapstoneproj to javafx.fxml;
    exports com.example.examsjavacapstoneproj;

    // Add this line to grant Gson access to your model package
    opens com.example.examsjavacapstoneproj.model to com.google.gson;
}