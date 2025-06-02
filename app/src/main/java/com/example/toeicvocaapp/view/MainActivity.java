package com.example.toeicvocaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocaapp.R;
import com.example.toeicvocaapp.adapter.TopicAdapter;
import com.example.toeicvocaapp.db.DatabaseHelper;
import com.example.toeicvocaapp.model.Topic;
import com.example.toeicvocaapp.viewmodel.TopicViewModel;
import com.example.toeicvocaapp.viewmodel.VocabularyViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private TopicViewModel topicViewModel;
    private VocabularyViewModel vocabViewModel;
    private TextView textViewWelcome, progressText;
    private ProgressBar progressBar;
    private Button addTopicButton, buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.topicRecyclerView);
        addTopicButton = findViewById(R.id.addTopicButton);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        progressText = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);
        buttonClose = findViewById(R.id.buttonClose);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ViewModels
        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        vocabViewModel = new ViewModelProvider(this).get(VocabularyViewModel.class);

        // Load topics
        topicViewModel.initFromJson();
        topicViewModel.getTopicList().observe(this, topics -> {
            adapter = new TopicAdapter(topics, topic -> {
                Intent intent = new Intent(MainActivity.this, VocabularyListActivity.class);
                intent.putExtra("topic_id", topic.getId());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        });

        // Observe daily progress
        vocabViewModel.getTodayWordCount().observe(this, count -> {
            progressBar.setProgress(count);
            progressText.setText("Daily Progress: " + count + "/10 words");
        });

        // Add topic button
        addTopicButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTopicActivity.class);
            startActivity(intent);
        });

        // Welcome message
        String name = getIntent().getStringExtra("name");
        textViewWelcome.setText("Welcome, " + name + "!");

        // Close button
        buttonClose.setOnClickListener(v -> finishAffinity());

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        topicViewModel.loadTopics(); // Refresh topics
    }
}