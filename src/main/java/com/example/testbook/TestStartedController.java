package com.example.testbook;

import com.example.testbook.Database.Database;
import com.example.testbook.Database.QuestionInformation;
import com.example.testbook.Database.SubjectInformation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class TestStartedController implements Initializable {
    @FXML
    private Button btnAnswer;

    @FXML
    private Button btnEnd;

    @FXML
    private Label countLabel;

    @FXML
    private Label numberOfQuestion;

    @FXML
    private Label question;

    @FXML
    private RadioButton variant1;

    @FXML
    private RadioButton variant2;

    @FXML
    private RadioButton variant3;

    @FXML
    private RadioButton variant4;

    @FXML
    private ToggleGroup variants;

    int id;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void test() {
        Random random = new Random();
        int randomNumber = random.nextInt(3);

        try {
            Database db = new Database();
            db.getSubjectInformation();
            db.getQuestionInformation();

            ArrayList<SubjectInformation> subjectsInformation = db.getSubjectsInformation();
            ArrayList<QuestionInformation> questionsInformation = db.getQuestionsInformation();
            ArrayList<Integer> count = new ArrayList<>();
            ArrayList<String> questions = new ArrayList<>();
            ArrayList<String> variants = new ArrayList<>();


            Connection conn;
            PreparedStatement ps;
            PreparedStatement ps2;
            PreparedStatement ps3;


            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");

            ps = conn.prepareStatement("SELECT count(*) FROM questions WHERE SubjectId = ?");
            ps.setInt(1, this.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count.add(rs.getInt("count(*)"));
            }


            ps2 = conn.prepareStatement("SELECT Question FROM questions WHERE SubjectId = ?");
            ps2.setInt(1, this.getId());
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                questions.add(rs2.getString("Question"));
            }


            ps3 = conn.prepareStatement("SELECT Variant1, Variant2, Variant3, Correct_answer FROM questions WHERE SubjectId = ?");
            ps3.setInt(1, this.getId());
            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {
                variants.add(rs3.getString("Variant1"));
                variants.add(rs3.getString("Variant2"));
                variants.add(rs3.getString("Variant3"));
                variants.add(rs3.getString("Correct_answer"));
            }


            question.setText(questions.get(0));

            for (int i = 0; i < 4; i++) {
                variant1.setText(variants.get(randomNumber));
                variant2.setText(variants.get(randomNumber));
                variant3.setText(variants.get(randomNumber));
                
            }

            for (SubjectInformation subject: subjectsInformation) {
                if (this.getId() == subject.getSubjectId()) {
//                    System.out.println(getId());
                }
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
