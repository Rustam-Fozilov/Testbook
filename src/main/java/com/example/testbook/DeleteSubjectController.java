package com.example.testbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

import java.sql.*;

public class DeleteSubjectController {
    @FXML
    private CheckBox confirmCheckbox;

    @FXML
    private Text statusText;

    int id;
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @FXML
    void deleteSubject() {
        if (confirmCheckbox.isSelected()) {
            try {
                Connection con;
                PreparedStatement ps;
                PreparedStatement ps2;

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");

                ps = con.prepareStatement("DELETE FROM subjects WHERE SubjectId = ?");
                ps.setInt(1, getId());

                System.out.println("keldi: " + getId());

                ps2 = con.prepareStatement("DELETE FROM questions WHERE SubjectId = ?");
                ps2.setInt(1, getId());

                ps.executeUpdate();
                ps2.executeUpdate();

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            statusText.setVisible(true);
        }
    }
}
