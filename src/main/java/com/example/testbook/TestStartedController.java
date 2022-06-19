package com.example.testbook;

import com.example.testbook.Database.Database;
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
    private Label questionLabel;

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

    private int nowQuestion = 0, raqam = 0, variantsIndex = 0, correctAnswers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void test() {
        // RANDOM INDEX OYLAB BERISH
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();

        while (list.size() < 4) {
            int candidateInt = random.nextInt(4);

            if (!list.contains(candidateInt)) {
                list.add(candidateInt);
            }
        }

        int count = 0;

        ArrayList<String> questionsList = new ArrayList<>();
        ArrayList<String> variantsList = new ArrayList<>();
        ArrayList<String> correctAnswerList = new ArrayList<>();

        try {
            Database db = new Database();
            db.getSubjectInformation();
            db.getQuestionInformation();

            Connection conn;
            PreparedStatement ps;
            PreparedStatement ps2;
            PreparedStatement ps3;
            PreparedStatement ps4;


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
                questionsList.add(rs2.getString("Question"));
            }


            ps3 = conn.prepareStatement("SELECT Variant1, Variant2, Variant3, Correct_answer FROM questions WHERE SubjectId = ?");
            ps3.setInt(1, this.getId());
            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {
                variantsList.add(rs3.getString("Variant1"));
                variantsList.add(rs3.getString("Variant2"));
                variantsList.add(rs3.getString("Variant3"));
                variantsList.add(rs3.getString("Correct_answer"));
            }


            ps4 = conn.prepareStatement("SELECT Correct_answer FROM questions WHERE SubjectId = ?");
            ps4.setInt(1, this.getId());
            ResultSet rs4 = ps4.executeQuery();

            while (rs4.next()) {
                correctAnswerList.add(rs4.getString("Correct_answer"));
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        countLabel.setText("1/" + count);
        numberOfQuestion.setText("1 - savol");
        questionLabel.setText(questionsList.get(0));
        variant1.setText(variantsList.get(list.get(0)));
        variant2.setText(variantsList.get(list.get(1)));
        variant3.setText(variantsList.get(list.get(2)));
        variant4.setText(variantsList.get(list.get(3)));


        btnAnswer.setOnMouseClicked(ActionEvent -> {
            RadioButton selectedRadio = (RadioButton) variants.getSelectedToggle();

            if (selectedRadio != null) {
                String toggleGroupValue = selectedRadio.getText();

                if (toggleGroupValue.equals(correctAnswerList.get(raqam))) {
                    raqam++;
                    correctAnswers++;
                    System.out.println("tori");
                } else {
                    raqam++;
                }

            }

            if (nowQuestion + 1 != questionsList.size()) {
                nowQuestion++;
                variantsIndex += 4;

                questionLabel.setText(questionsList.get(nowQuestion));
                variant1.setText(variantsList.get(variantsIndex));
                variant2.setText(variantsList.get(variantsIndex + 1));
                variant3.setText(variantsList.get(variantsIndex + 2));
                variant4.setText(variantsList.get(variantsIndex + 3));
            } else {
                endTest();
            }

            variant1.setSelected(false);
            variant2.setSelected(false);
            variant3.setSelected(false);
            variant4.setSelected(false);
        });

        btnEnd.setOnMouseClicked(ActionEventEnd -> {
            endTest();
        });

        id = getId();
    }

    public void endTest() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("result.fxml"));
            Parent root = loader.load();

            ResultController rc = loader.getController();
            rc.setId(getId());
            rc.showResults(correctAnswers);

            testPane.getChildren().removeAll();
            testPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
