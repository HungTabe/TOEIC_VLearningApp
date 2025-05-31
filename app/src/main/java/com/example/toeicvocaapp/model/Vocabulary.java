package com.example.toeicvocaapp.model;

public class Vocabulary {
    private int id;
    private String english;
    private String vietnamese;

    public Vocabulary(int id, String english, String vietnamese) {
        this.id = id;
        this.english = english;
        this.vietnamese = vietnamese;
    }

    public int getId() {
        return id;
    }

    public String getEnglish() {
        return english;
    }

    public String getVietnamese() {
        return vietnamese;
    }
}
