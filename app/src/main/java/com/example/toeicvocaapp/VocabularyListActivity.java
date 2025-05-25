package com.example.toeicvocaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Vocabulary;

import java.util.List;

public class VocabularyListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VocabularyAdapter adapter;
    private DatabaseHelper db;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_list);

        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.vocabRecyclerView);
        Button addVocabButton = findViewById(R.id.addVocabButton);
        Button learnButton = findViewById(R.id.learnButton);
        Button testButton = findViewById(R.id.testButton);

        topicId = getIntent().getIntExtra("topic_id", -1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateVocabList();

        testButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("topic_id", topicId);
            startActivity(intent);
        });

        addVocabButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddVocabularyActivity.class);
            intent.putExtra("topic_id", topicId);
            startActivity(intent);
        });

        learnButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FlashcardActivity.class);
            intent.putExtra("topic_id", topicId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateVocabList();
    }

    private void updateVocabList() {
        List<Vocabulary> vocabList = db.getVocabByTopic(topicId);
        adapter = new VocabularyAdapter(vocabList);
        recyclerView.setAdapter(adapter);
    }
}
