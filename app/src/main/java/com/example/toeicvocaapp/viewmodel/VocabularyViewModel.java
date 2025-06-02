package com.example.toeicvocaapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.toeicvocaapp.model.Vocabulary;
import com.example.toeicvocaapp.repository.VocabularyRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VocabularyViewModel extends AndroidViewModel {
    private VocabularyRepository repository;
    private MutableLiveData<List<Vocabulary>> vocabList;
    private MutableLiveData<List<Vocabulary>> learnedWords;
    private MutableLiveData<Integer> todayWordCount;

    public VocabularyViewModel(Application application) {
        super(application);
        repository = new VocabularyRepository(application);
        vocabList = new MutableLiveData<>();
        learnedWords = new MutableLiveData<>();
        todayWordCount = new MutableLiveData<>();
        updateTodayWordCount();
    }

    public LiveData<List<Vocabulary>> getVocabList() {
        return vocabList;
    }

    public LiveData<List<Vocabulary>> getLearnedWords() {
        return learnedWords;
    }

    public LiveData<Integer> getTodayWordCount() {
        return todayWordCount;
    }

    public void loadVocabByTopic(int topicId) {
        vocabList.setValue(repository.getVocabByTopic(topicId));
    }

    public void loadLearnedWords(int topicId) {
        learnedWords.setValue(repository.getLearnedWords(topicId));
    }

    public void addVocabulary(int topicId, String english, String vietnamese) {
        repository.addVocabulary(topicId, english, vietnamese);
        loadVocabByTopic(topicId);
    }

    public void addProgress(int wordId) {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        repository.addProgress(wordId, today);
        updateTodayWordCount();
    }

    private void updateTodayWordCount() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        todayWordCount.setValue(repository.countWordsToday(today));
    }
}