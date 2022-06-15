package com.example.testbook;

import com.example.testbook.Database.Database;
import com.example.testbook.Database.QuestionInformation;
import com.example.testbook.Database.SubjectInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    private AnchorPane testPane;


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
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();

        while (list.size() < 4) {
            int candidateInt = random.nextInt(4);

            if (!list.contains(candidateInt)) {
                list.add(candidateInt);
            }
        }


        try {
            Database db = new Database();
            db.getSubjectInformation();
            db.getQuestionInformation();

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
            variant1.setText(variants.get(list.get(0)));
            variant2.setText(variants.get(list.get(1)));
            variant3.setText(variants.get(list.get(2)));
            variant4.setText(variants.get(list.get(3)));

            id = getId();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void answered() {
        boolean isSelected = true;

//        for (int i = 0; i < 4; i++) {
//            if (variants.getToggles().get(i).isSelected()) {
//                isSelected = false;
//            } else {
//                isSelected  = true;
//            }
//        }

        if (isSelected) {
            ArrayList<String> correctAnswers = new ArrayList<>();
            int count = 0;

            try {
                Database db = new Database();
                db.getSubjectInformation();
                db.getQuestionInformation();

                ArrayList<SubjectInformation> subjectsInformation = db.getSubjectsInformation();
                ArrayList<QuestionInformation> questionsInformation = db.getQuestionsInformation();
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
                    count = rs.getInt("count(*)");
                }


                ps2 = conn.prepareStatement("SELECT Question FROM questions WHERE SubjectId = ?");
                ps2.setInt(1, this.getId());
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    questions.add(rs2.getString("Question"));
                }


                countLabel.setText("2/" + count);
                numberOfQuestion.setText("2 - Savol");
                question.setText(questions.get(1));
                variant1.setSelected(false);
                variant2.setSelected(false);
                variant3.setSelected(false);
                variant4.setSelected(false);

                int finalCount = count;
                btnAnswer.setOnAction(actionEvent -> {
                    for (int i = 2; i < finalCount; i++) {
                        question.setText(questions.get(i));
                        countLabel.setText((i + 1) + "/" + finalCount);
                        numberOfQuestion.setText((i + 1) + " - Savol");
                        variant1.setSelected(false);
                        variant2.setSelected(false);
                        variant3.setSelected(false);
                        variant4.setSelected(false);
                    }
                });

            } catch (SQLException e) {
                System.out.println(e);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void endTest(ActionEvent actionEvent) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("result.fxml"));
            testPane.getChildren().removeAll();
            testPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
