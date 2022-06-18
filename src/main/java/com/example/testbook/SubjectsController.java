package com.example.testbook;

import com.example.testbook.Database.Database;
import com.example.testbook.Database.SubjectInformation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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
                "-fx-opacity: 0.5;" +
                "-fx-font-family: System Regular;"
        );
        infoVBox.setAlignment(Pos.CENTER);
        infoVBox.setSpacing(230);

        AtomicInteger count = new AtomicInteger();
        try {
            Database database = new Database();
            database.getSubjectInformation();
            ArrayList<SubjectInformation> subjectsInformation = database.getSubjectsInformation();

            int finalI;
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
                        "-fx-font-size: 18px;" +
                        "-fx-cursor: hand;"
                );

                finalI = i;
                int finalI1 = finalI;
                int finalI2 = i;
                tilePane.getChildren().get(i).setOnMouseClicked(mouseEvent -> {
                    startButton.setStyle(
                            "-fx-pref-width: 160px;" +
                            "-fx-pref-height: 50px;" +
                            "-fx-background-color: #0066FF;" +
                            "-fx-background-radius: 12px;" +
                            "-fx-text-fill: #fff;" +
                            "-fx-font-size: 18px;" +
                            "-fx-cursor: hand;"
                    );

                    Connection conn;
                    PreparedStatement ps;

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");


                        ps = conn.prepareStatement("SELECT count(*) FROM questions WHERE SubjectId = ?");
                        ps.setInt(1, finalI2 + 1);
                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            count.set(rs.getInt("count(*)"));
                        }

                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }

                    infoText.setText(
                            "Nomi: " + subjectsInformation.get(finalI1).getName() + "\n" +
                            "Testlar soni: " + count.get() + "\n"
                    );
                    fanlarTitle.setVisible(true);
                    startButton.setVisible(true);

                    startButton.setOnMouseClicked(mouseEvent2 -> {
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
