package com.example.toeicvocaapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Topic;

import java.util.List;

public class TopicViewModel extends AndroidViewModel {
    private DatabaseHelper db;
    private MutableLiveData<List<Topic>> topicList;

    public TopicViewModel(Application application) {
        super(application);
        db = new DatabaseHelper(application);
        topicList = new MutableLiveData<>();
        loadTopics();
    }

    public LiveData<List<Topic>> getTopicList() {
        return topicList;
    }

    public void loadTopics() {
        topicList.setValue(db.getAllTopics());
    }

    public void addTopic(String name) {
        db.addTopic(name);
        loadTopics();
    }

    public void initFromJson() {
        db.initFromJson(getApplication());
        loadTopics();
    }
}
