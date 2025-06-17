module com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.project3.gui to javafx.fxml;
    exports com.example.project3.gui;

    opens com.example.project3.clinic to javafx.fxml;
    exports com.example.project3.clinic;

    opens com.example.project3.persons to javafx.fxml;
    exports com.example.project3.persons;

    opens com.example.project3.util to javafx.fxml;
    exports com.example.project3.util;
}
