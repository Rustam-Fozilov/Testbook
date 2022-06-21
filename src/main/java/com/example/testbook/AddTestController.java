package com.example.testbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTestController {
    @FXML
    private TextField correctAnswerField;

    @FXML
    private TextField questionField;

    @FXML
    private Text statusText;

    @FXML
    private TextField variant1Field;

    @FXML
    private TextField variant2Field;

    @FXML
    private TextField variant3Field;

    int id;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void getSubjectId(int sid) {
        setId(sid);
    }

    @FXML
    void addTest(ActionEvent event) {
        if (correctAnswerField.getText().isEmpty() || variant1Field.getText().isEmpty() || variant2Field.getText().isEmpty() || variant3Field.getText().isEmpty() || correctAnswerField.getText().isEmpty()) {
            statusText.setText("Barcha maydonlar to'ldirilishi shart !");
            statusText.setStyle("-fx-fill: red;");
        } else {
            try {
                Connection con;
                PreparedStatement ps;

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");

                ps = con.prepareStatement("INSERT INTO questions (SubjectId, Question, Variant1, Variant2, Variant3, Correct_answer) VALUES (?, ?, ?, ?, ?, ?)");
                ps.setInt(1, getId());
                ps.setString(2, questionField.getText());
                ps.setString(3, variant1Field.getText());
                ps.setString(4, variant2Field.getText());
                ps.setString(5, variant3Field.getText());
                ps.setString(6, correctAnswerField.getText());
                ps.executeUpdate();

                statusText.setText("Muvaffaqiyatli qo'shildi");
                statusText.setStyle("-fx-fill: green;");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
