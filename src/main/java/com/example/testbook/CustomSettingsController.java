package com.example.testbook;

import com.example.testbook.Database.Database;
import com.example.testbook.Database.QuestionsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomSettingsController implements Initializable {
    @FXML
    private Text updateTestText;

    @FXML
    private Text addTestText;

    @FXML
    private Text deleteSubjectText;

    @FXML
    private TableColumn<QuestionsTable, String> correctAnswerColumn;

    @FXML
    private TableColumn<QuestionsTable, String> questionColumn;

    @FXML
    private TableColumn<QuestionsTable, Integer> questionIdColumn;

    @FXML
    private TableColumn<QuestionsTable, String> variant1Column;

    @FXML
    private TableColumn<QuestionsTable, String> variant2Column;

    @FXML
    private TableColumn<QuestionsTable, String> variant3Column;

    @FXML
    private TableColumn<QuestionsTable, String> editColumn;

    @FXML
    private TableView<QuestionsTable> questionsTableView;

    @FXML
    private VBox settingsVbox;

    ObservableList<QuestionsTable> questionsList = FXCollections.observableArrayList();

    int id;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void showEditTable() {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT QuestionsId, Question, Variant1, Variant2, Variant3, Correct_answer FROM questions WHERE SubjectId = ?");
            ps.setInt(1, this.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                questionsList.add(new QuestionsTable(
                        rs.getInt("QuestionsId"),
                        rs.getString("Question"),
                        rs.getString("Variant1"),
                        rs.getString("Variant2"),
                        rs.getString("Variant3"),
                        rs.getString("Correct_answer")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        questionIdColumn.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        variant1Column.setCellValueFactory(new PropertyValueFactory<>("variant1"));
        variant2Column.setCellValueFactory(new PropertyValueFactory<>("variant2"));
        variant3Column.setCellValueFactory(new PropertyValueFactory<>("variant3"));
        correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        questionsTableView.setItems(questionsList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        showEditTable();
    }
}
