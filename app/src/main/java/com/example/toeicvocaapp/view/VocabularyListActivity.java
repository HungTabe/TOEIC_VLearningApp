package com.example.toeicvocaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.adapter.VocabularyAdapter;
import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Vocabulary;
import com.example.toeicvocaapp.viewmodel.TopicViewModel;
import com.example.toeicvocaapp.viewmodel.VocabularyViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class VocabularyListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VocabularyAdapter adapter;
    private DatabaseHelper db;
    private int topicId;
    private VocabularyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_list);

        recyclerView = findViewById(R.id.vocabRecyclerView);
        Button addVocabButton = findViewById(R.id.addVocabButton);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        topicId = getIntent().getIntExtra("topic_id", -1);
        viewModel = new ViewModelProvider(this).get(VocabularyViewModel.class);
        viewModel.loadVocabByTopic(topicId);
        viewModel.getVocabList().observe(this, vocabList -> {
            adapter = new VocabularyAdapter(vocabList);
            recyclerView.setAdapter(adapter);
        });

        addVocabButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddVocabularyActivity.class);
            intent.putExtra("topic_id", topicId);
            startActivity(intent);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.nav_learn) {
                intent = new Intent(this, FlashcardActivity.class);
                intent.putExtra("topic_id", topicId);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.nav_test) {
                intent = new Intent(this, TestActivity.class);
                intent.putExtra("topic_id", topicId);
                startActivity(intent);
                return true;
            }
            return false;
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
