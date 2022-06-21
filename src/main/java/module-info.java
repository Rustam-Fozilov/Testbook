module com.example.testbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.testbook to javafx.fxml;
    exports com.example.testbook;
    exports com.example.testbook.Database;
    opens com.example.testbook.Database to javafx.fxml;
}