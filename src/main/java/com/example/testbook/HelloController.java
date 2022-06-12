package com.example.testbook;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private VBox navbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navbar.setAlignment(Pos.CENTER);
    }
}