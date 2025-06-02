package com.example.toeicvocaapp.repository;

import android.content.Context;

import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Topic;
import com.example.toeicvocaapp.model.Vocabulary;

import java.util.List;

public class VocabularyRepository {
    private DatabaseHelper dbHelper;

    public VocabularyRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void initFromJson(Context context) {
        dbHelper.initFromJson(context);
    }

    public List<Topic> getAllTopics() {
        return dbHelper.getAllTopics();
    }

    public void addTopic(String name) {
        dbHelper.addTopic(name);
    }

    public List<Vocabulary> getVocabByTopic(int topicId) {
        return dbHelper.getVocabByTopic(topicId);
    }

    public void addVocabulary(int topicId, String english, String vietnamese) {
        dbHelper.addVocabulary(topicId, english, vietnamese);
    }

    public void addProgress(int wordId, String date) {
        dbHelper.addProgress(wordId, date);
    }

    public int countWordsToday(String date) {
        return dbHelper.countWordsToday(date);
    }

    public List<Vocabulary> getLearnedWords(int topicId) {
        return dbHelper.getLearnedWords(topicId);
    }
}
