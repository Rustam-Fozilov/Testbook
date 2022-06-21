package com.example.testbook.Database;

public class QuestionsTable {
    private int questionId;
    private String question;
    private String variant1;
    private String variant2;
    private String variant3;
    private String correctAnswers;

    public QuestionsTable(int questionId, String question, String variant1, String variant2, String variant3, String correctAnswers) {
        this.questionId = questionId;
        this.question = question;
        this.variant1 = variant1;
        this.variant2 = variant2;
        this.variant3 = variant3;
        this.correctAnswers = correctAnswers;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getVariant1() {
        return variant1;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }

    public String getVariant3() {
        return variant3;
    }

    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
