package com.example.testbook.Database;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    ArrayList<SubjectInformation> subjectsInformation = new ArrayList<>();

    public void getSubjectInformation() throws IOException, ClassNotFoundException {
        try {
            Connection con;
            PreparedStatement ps;
            ResultSet rs;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");
            ps = con.prepareStatement("SELECT * FROM subjects");
            rs = ps.executeQuery();

            while (rs.next()) {
                int subjectId = rs.getInt("SubjectId");
                String name = rs.getString("Name");
                int testAmount = rs.getInt("Test_amount");
                Time duration = rs.getTime("Duration");

                subjectsInformation.add(new SubjectInformation(subjectId, name, testAmount, duration));
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<SubjectInformation> getSubjectsInformation() {
        return subjectsInformation;
    }


    ArrayList<QuestionInformation> questionsInformation = new ArrayList<>();

    public void getQuestionInformation() throws IOException, ClassNotFoundException {
        try {
            Connection con;
            PreparedStatement ps;
            ResultSet rs;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");
            ps = con.prepareStatement("SELECT * FROM questions");
            rs = ps.executeQuery();

            while (rs.next()) {
                int questionId = rs.getInt("QuestionsId");
                int subjectId = rs.getInt("SubjectId");
                String question = rs.getString("Question");
                String variant1 = rs.getString("Variant1");
                String variant2 = rs.getString("Variant2");
                String variant3 = rs.getString("Variant3");
                String correctAnswer = rs.getString("Correct_answer");

                questionsInformation.add(new QuestionInformation(subjectId, questionId, question, variant1, variant2, variant3, correctAnswer));
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<QuestionInformation> getQuestionsInformation() {
        return questionsInformation;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testbook", "root", "1234");
        return conn;
    }
}
