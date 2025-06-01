package com.example.toeicvocaapp.model;

public class LearningProgress {
    private int id;
    private int wordId;
    private String date;
    private String status;

    public LearningProgress(int id, int wordId, String date, String status) {
        this.id = id;
        this.wordId = wordId;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getWordId() {
        return wordId;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
