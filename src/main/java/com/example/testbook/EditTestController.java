package com.example.testbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditTestController {
    @FXML
    private TextField correctAnswerField;

    @FXML
    private TextField questionField;

    @FXML
    private TextField variant1Field;

    @FXML
    private TextField variant2Field;

    @FXML
    private TextField variant3Field;

    @FXML
    private Text statusLabel;

    int id;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }



    public void setTextField(int qid, String question, String variant1, String variant2, String variant3, String correctAnswer) {
        questionField.setText(question);
        variant1Field.setText(variant1);
        variant2Field.setText(variant2);
        variant3Field.setText(variant3);
        correctAnswerField.setText(correctAnswer);

        setId(qid);
    }

    @FXML
    void updateTests(ActionEvent event) {
        if (questionField.getText().isEmpty() || variant1Field.getText().isEmpty() || variant2Field.getText().isEmpty() || variant3Field.getText().isEmpty() || correctAnswerField.getText().isEmpty()) {
            statusLabel.setText("Barcha maydonlar to'ldirilishi shart !");
            statusLabel.setStyle("-fx-fill: red;");
        } else {
            try {
                Connection con;
                PreparedStatement ps;

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");

                ps = con.prepareStatement("UPDATE questions SET Question = ?, Variant1 = ?, Variant2 = ?, Variant3 = ?, Correct_answer = ? WHERE QuestionsId = ?");
                ps.setString(1, questionField.getText());
                ps.setString(2, variant1Field.getText());
                ps.setString(3, variant2Field.getText());
                ps.setString(4, variant3Field.getText());
                ps.setString(5, correctAnswerField.getText());
                ps.setInt(6, getId());
                ps.executeUpdate();

                statusLabel.setText("Ma'lumotlar yangilandi");
                statusLabel.setStyle("-fx-fill: green;");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
