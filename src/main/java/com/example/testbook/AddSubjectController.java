package com.example.testbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddSubjectController {
    @FXML
    private Text statusLabel;

    @FXML
    private TextField subjectNameField;

    @FXML
    private AnchorPane addSubjectPane;

    @FXML
    void addSubject(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (subjectNameField.getText().isEmpty()) {
            statusLabel.setText("Maydon to'ldirilishi shart !");
            statusLabel.setStyle("-fx-fill: red;");
        } else {
            Connection con;
            PreparedStatement ps;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");

            ps = con.prepareStatement("INSERT INTO Subjects (Name) VALUES (?)");
            ps.setString(1, subjectNameField.getText());
            ps.executeUpdate();

            statusLabel.setText("Muvaffaqiyatli qo'shildi");
            statusLabel.setStyle("-fx-fill: green;");
        }
    }

    @FXML
    void goToBack(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("subjects.fxml"));
            addSubjectPane.getChildren().removeAll();
            addSubjectPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
