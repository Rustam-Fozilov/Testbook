package com.example.testbook;

import com.example.testbook.Database.Database;
import com.example.testbook.Database.QuestionInformation;
import com.example.testbook.Database.SubjectInformation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubjectsController implements Initializable {

    @FXML
    private Text fanlarTitle;

    @FXML
    private Text infoText;

    @FXML
    private VBox infoVBox;

    @FXML
    private Button startButton;

    @FXML
    private TilePane tilePane;

    @FXML
    private Text title;

    @FXML
    private AnchorPane fanlarPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button addTestButton = new Button("+ Test qo'shish");
        addTestButton.setId("addTestButton");

        addTestButton.setStyle(
                "-fx-pref-width: 160px;" +
                "-fx-pref-height: 50px;" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: #151515;" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;" +
                "-fx-opacity: 0.5;"
        );
        infoVBox.setAlignment(Pos.CENTER);
        infoVBox.setSpacing(230);

        try {
            Database database = new Database();
            database.getSubjectInformation();
            ArrayList<SubjectInformation> subjectsInformation = database.getSubjectsInformation();
            ArrayList<QuestionInformation> questionsInformation = database.getQuestionsInformation();

            int finalI = 0;
            for (int i = 0; i < subjectsInformation.size(); i++) {
                tilePane.getChildren().addAll(new Button(subjectsInformation.get(i).getName()));
                tilePane.setHgap(20);
                tilePane.setVgap(20);
                tilePane.getChildren().get(i).setId("Button" + (i + 1));
                tilePane.getChildren().get(i).setStyle(
                        "-fx-pref-width: 160px;" +
                        "-fx-pref-height: 50px;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #151515;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-font-family: Montserrat SemiBold;" +
                        "-fx-font-size: 18px;" +
                        "-fx-cursor: hand;"
                );

                finalI = i;
                int finalI1 = finalI;
                tilePane.getChildren().get(i).setOnMouseClicked(mouseEvent -> {
                    infoText.setText(
                            "Nomi: " + subjectsInformation.get(finalI1).getName() + "\n" +
                            "Testlar soni: " + subjectsInformation.get(finalI1).getTestAmount() + "\n" +
                            "Davomiyligi: " + subjectsInformation.get(finalI1).getDuration()

                    );
                    fanlarTitle.setVisible(true);
                    startButton.setVisible(true);

                    startButton.setOnMouseClicked(mouseEvent2 -> {
//                        System.out.println(finalI1 + 1);

                        try {
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("test-started.fxml"));
                            Parent root = loader.load();

                            TestStartedController ts = loader.getController();
                            ts.setId(finalI1 + 1);
                            ts.test();

                            fanlarPane.getChildren().removeAll();
                            fanlarPane.getChildren().setAll(root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
            }

            tilePane.getChildren().add(addTestButton);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
