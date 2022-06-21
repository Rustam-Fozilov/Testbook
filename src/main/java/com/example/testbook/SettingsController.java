package com.example.testbook;

import com.example.testbook.Database.Database;
import com.example.testbook.Database.QuestionsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;

public class SettingsController {
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

    public void refreshTable() {
        try {
            questionsList.clear();

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

            questionsTableView.setItems(questionsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswers"));


        Callback<TableColumn<QuestionsTable, String>, TableCell<QuestionsTable, String>> cellFactory = (TableColumn<QuestionsTable, String> param) -> {
            final TableCell<QuestionsTable, String> cell = new TableCell<QuestionsTable, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView deleteIcon = new ImageView("delete-icon.png");
                        ImageView editIcon = new ImageView("edit-icon.png");

                        deleteIcon.setStyle(
                                "-fx-cursor: hand;"
                        );

                        editIcon.setStyle(
                                "-fx-cursor: hand;"
                        );

                        deleteIcon.setOnMouseClicked(MouseEvent -> {
                            QuestionsTable qt = questionsTableView.getSelectionModel().getSelectedItem();
                            try {
                                Connection conn = Database.getConnection();
                                PreparedStatement ps = conn.prepareStatement("DELETE FROM questions WHERE QuestionsId = " + qt.getQuestionId());
                                ps.executeUpdate();

                                refreshTable();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                        editIcon.setOnMouseClicked(MouseEvent -> {
                            QuestionsTable qt = questionsTableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("edit-test.fxml"));

                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            EditTestController etc = loader.getController();
                            etc.setTextField(qt.getQuestionId(), qt.getQuestion(), qt.getVariant1(), qt.getVariant2(), qt.getVariant3(), qt.getCorrectAnswers());

                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });

                        HBox manageBtn = new HBox(editIcon, deleteIcon);
                        manageBtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 2, 8));
                        HBox.setMargin(editIcon, new Insets(2, 8, 2, 2));

                        setGraphic(manageBtn);
                        setText(null);
                    }
                }
            };

            return cell;
        };

        editColumn.setCellFactory(cellFactory);
        questionsTableView.setItems(questionsList);
    }

    public void addTest(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add-test.fxml"));
            Parent root = loader.load();

            AddTestController atc = loader.getController();
            atc.getSubjectId(this.getId());

            settingsVbox.getChildren().removeAll();
            settingsVbox.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deleteSubjectText.setStyle("-fx-fill: black");
        addTestText.setStyle("-fx-fill: #0066FF");
    }

    public void deleteSubject(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("delete-subject.fxml"));
            Parent root = loader.load();

            DeleteSubjectController dsc = loader.getController();
            dsc.setId(this.getId());
            dsc.deleteSubject();

            settingsVbox.getChildren().removeAll();
            settingsVbox.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deleteSubjectText.setStyle("-fx-fill: #0066FF");
        addTestText.setStyle("-fx-fill: black");
    }
}
