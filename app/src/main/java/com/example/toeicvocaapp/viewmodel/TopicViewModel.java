package com.example.toeicvocaapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.toeicvocaapp.model.Topic;
import com.example.toeicvocaapp.repository.VocabularyRepository;

import java.util.List;

public class TopicViewModel extends AndroidViewModel {
    private VocabularyRepository repository;
    private MutableLiveData<List<Topic>> topicList;

    public TopicViewModel(Application application) {
        super(application);
        repository = new VocabularyRepository(application);
        topicList = new MutableLiveData<>();
        loadTopics();
    }

    public LiveData<List<Topic>> getTopicList() {
        return topicList;
    }

    public void loadTopics() {
        topicList.setValue(repository.getAllTopics());
    }

    public void addTopic(String name) {
        repository.addTopic(name);
        loadTopics();
    }

    public void initFromJson() {
        repository.initFromJson(getApplication());
        loadTopics();
    }
}
