module com.example.testbook {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testbook to javafx.fxml;
    exports com.example.testbook;
}