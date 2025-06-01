package com.example.toeicvocaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
import com.example.toeicvocaapp.viewmodel.TopicViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton; // Thêm import này

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private TopicViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.topicRecyclerView);
        FloatingActionButton addTopicButton = findViewById(R.id.addTopicButton); // Sửa từ Button thành FloatingActionButton        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        viewModel.initFromJson();
        viewModel.getTopicList().observe(this, topics -> {
            adapter = new TopicAdapter(topics, topic -> {
                Intent intent = new Intent(MainActivity.this, VocabularyListActivity.class);
                intent.putExtra("topic_id", topic.getId());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        });

        addTopicButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTopicActivity.class);
            startActivity(intent);
        });
    }
}