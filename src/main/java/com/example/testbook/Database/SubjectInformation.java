package com.example.testbook.Database;

import java.sql.Time;

public class SubjectInformation {
    int subjectId;
    String name;
    int testAmount;
    Time duration;

    public SubjectInformation(int subjectId, String name, int test_amount, Time duration) {
        this.subjectId = subjectId;
        this.name = name;
        this.testAmount = test_amount;
        this.duration = duration;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTestAmount() {
        return testAmount;
    }

    public void setTestAmount(int testAmount) {
        this.testAmount = testAmount;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }
}
