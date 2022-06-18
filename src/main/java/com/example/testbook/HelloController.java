package com.example.testbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private VBox navbar;

    @FXML
    private ImageView homeIcon;

    @FXML
    private AnchorPane bgSubjectsPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navbar.setAlignment(Pos.CENTER);

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("subjects.fxml"));
            bgSubjectsPane.getChildren().removeAll();
            bgSubjectsPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToHome(MouseEvent mouseEvent) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("subjects.fxml"));
            bgSubjectsPane.getChildren().removeAll();
            bgSubjectsPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
