package com.example.testbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

public class ResultController {
    @FXML
    private Label allQuestionsLabel;

    @FXML
    private Label corrAnswersLabel;

    @FXML
    private Label percentLabel;

    @FXML
    private AnchorPane resultPane;

    int id;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void showResults(int correctAnswers) {
        int count = 0;

        try {
            Connection conn;
            PreparedStatement ps;

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");

            ps = conn.prepareStatement("SELECT count(*) FROM questions WHERE SubjectId = ?");
            ps.setInt(1, this.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count = rs.getInt("count(*)");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        allQuestionsLabel.setText("Jami savol: " + count);
        corrAnswersLabel.setText("To'g'ri javoblar: " + correctAnswers);
        percentLabel.setText("Natija: " + correctAnswers * 100 / count + "%");
    }

    @FXML
    void goToHome(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("subjects.fxml"));
            resultPane.getChildren().removeAll();
            resultPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
